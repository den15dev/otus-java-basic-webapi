package ru.otus.java.basic.webapi.application.server;

import ru.otus.java.basic.webapi.application.exception.RequestTooLargeException;
import ru.otus.java.basic.webapi.application.request.HttpMethod;
import ru.otus.java.basic.webapi.application.request.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public Request getRequest(InputStream inputStream, int requestMaxSize)
            throws IOException, RequestTooLargeException {
        ByteArrayOutputStream requestBytes = new ByteArrayOutputStream();
        byte[] chunk = new byte[8192];

        int totalRead = 0;
        int bytesRead = inputStream.read(chunk);

        if (bytesRead == -1) {
            return null;
        }

        while (bytesRead != -1) {
            totalRead += bytesRead;

            if (totalRead > requestMaxSize) {
                throw new RequestTooLargeException(requestMaxSize);
            }

            requestBytes.write(chunk, 0, bytesRead);

            if (bytesRead < chunk.length) {
                break;
            }

            bytesRead = inputStream.read(chunk);
        }

        String rawRequest = requestBytes.toString(StandardCharsets.UTF_8);

        return parse(rawRequest);
    }


    private Request parse(String rawRequest) {
        Map<String, String> queryParams = new HashMap<>();
        String url;
        String body = null;

        int firstSpace = rawRequest.indexOf(' ');
        int secondSpace = rawRequest.indexOf(' ', firstSpace + 1);

        HttpMethod method = HttpMethod.valueOf(rawRequest.substring(0, firstSpace));
        String fullPath = rawRequest.substring(firstSpace + 1, secondSpace);

        int questionMark = fullPath.indexOf('?');
        if (questionMark >= 0) {
            url = fullPath.substring(0, questionMark);
            parseQueryParams(fullPath, questionMark + 1, queryParams);

        } else {
            url = fullPath;
        }

        int bodyStart = rawRequest.indexOf("\r\n\r\n");
        if (bodyStart >= 0) {
            body = rawRequest.substring(bodyStart + 4);
        }

        return new Request(method, url, queryParams, body);
    }


    private void parseQueryParams(String fullPath, int start, Map<String, String> queryParams) {
        int i = start;

        while (i < fullPath.length()) {
            int amp = fullPath.indexOf('&', i);
            if (amp == -1) {
                amp = fullPath.length();
            }

            int eq = fullPath.indexOf('=', i);
            if (eq != -1 && eq < amp) {
                String key = fullPath.substring(i, eq);
                String value = fullPath.substring(eq + 1, amp);
                queryParams.put(key, value);

            } else {
                String key = fullPath.substring(i, amp);
                queryParams.put(key, "");
            }

            i = amp + 1;
        }
    }
}
