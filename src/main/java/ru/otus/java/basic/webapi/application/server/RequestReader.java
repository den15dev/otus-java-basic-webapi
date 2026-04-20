package ru.otus.java.basic.webapi.application.server;

import ru.otus.java.basic.webapi.application.exception.RequestTooLargeException;
import ru.otus.java.basic.webapi.application.request.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RequestReader {
    public Request read(InputStream inputStream, int requestMaxSize)
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
        return new Request(rawRequest);
    }
}
