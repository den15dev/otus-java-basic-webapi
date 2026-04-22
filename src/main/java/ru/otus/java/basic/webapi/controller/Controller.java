package ru.otus.java.basic.webapi.controller;

import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.Response;

public abstract class Controller {
    public abstract Response handle(Request request);
}
