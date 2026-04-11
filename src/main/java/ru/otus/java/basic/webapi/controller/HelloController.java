package ru.otus.java.basic.webapi.controller;

import ru.otus.java.basic.webapi.application.response.HtmlResponse;

public class HelloController extends Controller {
    public HtmlResponse handle() {
        return new HtmlResponse("<html><body>Hello!</body></html>");
    }
}
