package ru.otus.java.basic.webapi.controller;

import ru.otus.java.basic.webapi.application.request.HttpRequest;
import ru.otus.java.basic.webapi.application.response.Response;

public abstract class Controller {
    public abstract Response handle(HttpRequest request);
}
