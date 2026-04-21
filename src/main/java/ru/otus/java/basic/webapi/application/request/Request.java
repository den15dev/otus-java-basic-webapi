package ru.otus.java.basic.webapi.application.request;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final HttpMethod method;
    private final String url;
    private final Map<String, String> queryParams;
    private Map<String, String> urlParams;
    private final String body;
    private final QueryParser queryParser;


    public Request(
            HttpMethod method,
            String url,
            Map<String, String> queryParams,
            String body
    ) {
        this.method = method;
        this.url = url;
        this.queryParams = queryParams;
        this.body = body;

        this.urlParams = new HashMap<>();
        this.queryParser = new QueryParser(queryParams);
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


    public QueryParser parseQuery() {
        return queryParser;
    }


    public void setUrlParams(Map<String, String> pathVariables) {
        this.urlParams = pathVariables;
    }


    public String getUrlParameter(String name) {
        return urlParams.get(name);
    }
}
