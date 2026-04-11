package ru.otus.java.basic.webapi.application.routing;

import ru.otus.java.basic.webapi.controller.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterizedRoute extends Route {
    public ParameterizedRoute(String method, String path, Controller controller) {
        super(method, path, controller);
    }


    public Map<String, String> match(String requestMethod, String requestPath) {
        if (!getMethod().name().equalsIgnoreCase(requestMethod)) {
            return null;
        }

        String[] templateParts = getPath().split("/");
        String[] requestParts = requestPath.split("/");

        List<String> cleanTemplateParts = Arrays.stream(templateParts)
                .filter(s -> !s.isEmpty())
                .toList();

        List<String> cleanRequestParts = Arrays.stream(requestParts)
                .filter(s -> !s.isEmpty())
                .toList();

        if (cleanTemplateParts.size() != cleanRequestParts.size()) {
            return null;
        }

        Map<String, String> pathVariables = new HashMap<>();

        for (int i = 0; i < cleanTemplateParts.size(); i++) {
            String templatePart = cleanTemplateParts.get(i);
            String requestPart = cleanRequestParts.get(i);

            if (templatePart.startsWith("{") && templatePart.endsWith("}")) {
                String varName = templatePart.substring(1, templatePart.length() - 1);
                pathVariables.put(varName, requestPart);

            } else if (!templatePart.equals(requestPart)) {
                return null;
            }
        }

        return pathVariables;
    }
}
