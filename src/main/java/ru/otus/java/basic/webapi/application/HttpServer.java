package ru.otus.java.basic.webapi.application;

import ru.otus.java.basic.webapi.application.request.HttpRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final Config config;
    private final Dispatcher dispatcher;

    public HttpServer(Config config) {
        this.config = config;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        int port = Integer.parseInt(config.get("server.port"));
        int threadNum = Integer.parseInt(config.get("server.thread-pool-size"));

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            ExecutorService service = Executors.newFixedThreadPool(threadNum);

            while (true) {
                Socket socket = serverSocket.accept();

                service.execute(() -> {
                    try (Socket clientSocket = socket) {
                        byte[] buffer = new byte[8192];
                        int n = clientSocket.getInputStream().read(buffer);
                        if (n < 1) {
                            return;
                        }

                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);
                        System.out.println("-----\n");

                        dispatcher.dispatch(request, clientSocket.getOutputStream());

                    } catch (IOException e) {
                        System.err.println("An error has occurred during request handling: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
