package ru.otus.java.basic.webapi;

import ru.otus.java.basic.webapi.application.Config;

public class App {
    public static void main(String[] args) {
        Config config = new Config();
        System.out.println(config.get("http.max-request-size-bytes"));
    }
}
