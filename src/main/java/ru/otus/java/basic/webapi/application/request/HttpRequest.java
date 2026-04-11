package ru.otus.java.basic.webapi.application.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String rawRequest;
    private HttpMethod method;
    private String url;
    private Map<String, String> queryParams;
    private Map<String, String> urlParams;
    private String body;


    public HttpRequest(String rawRequest) {
        this.urlParams = new HashMap<>();
        this.rawRequest = rawRequest;
        this.parse();
    }


    public HttpMethod getMethod() {
        return method;
    }


    public String getUrl() {
        return url;
    }


    public String getBody() {
        return body;
    }


    public String getExactRouteKey() {
        return method + " " + url;
    }


    public String getQueryParameter(String key) {
        return queryParams.get(key);
    }


    public Map<String, String> getUrlParams() {
        return urlParams;
    }


    public void setUrlParams(Map<String, String> pathVariables) {
        this.urlParams = pathVariables;
    }


    public String getUrlParameter(String name) {
        return urlParams.get(name);
    }


    public void info(boolean showRawRequest) {
        if (showRawRequest) {
            System.out.println(rawRequest);
        }
        System.out.println("Method: " + method);
        System.out.println("URI: " + url);
        System.out.println("Parameters: " + queryParams);
        System.out.println("Body: " + body);
    }


    private void parse() {
        queryParams = new HashMap<>();
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        url = rawRequest.substring(startIndex + 1, endIndex);

        if (url.contains("?")) {
            String[] elements = url.split("[?]");
            url = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
    }
}
