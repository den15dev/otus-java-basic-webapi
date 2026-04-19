package ru.otus.java.basic.webapi.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.webapi.application.exception.RequestTooLargeException;
import ru.otus.java.basic.webapi.application.request.Request;
import ru.otus.java.basic.webapi.application.response.HttpStatus;
import ru.otus.java.basic.webapi.application.response.JsonResponse;
import ru.otus.java.basic.webapi.application.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private final int threadNum;
    private final int requestMaxSize;
    private final RequestReader requestReader;
    private final Dispatcher dispatcher;
    private static final Logger logger = LogManager.getLogger(HttpServer.class);


    public HttpServer(Config config) {
        this.port = Integer.parseInt(config.get("server.port"));
        this.threadNum = Integer.parseInt(config.get("server.thread-pool-size"));
        this.requestMaxSize = Integer.parseInt(config.get("http.max-request-size-bytes"));
        this.requestReader = new RequestReader();

        ApplicationContext appContext = new ApplicationContext(config);
        this.dispatcher = new Dispatcher(appContext);
    }


    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            ExecutorService service = Executors.newFixedThreadPool(threadNum);

            while (true) {
                Socket socket = serverSocket.accept();
                service.execute(() -> handleClient(socket));
            }

        } catch (IOException e) {
            logger.error("I/O error during starting the server", e);
        }
    }


    private void handleClient(Socket socket) {
        try (Socket clientSocket = socket) {
            Request request;

            try {
                InputStream inputStream = clientSocket.getInputStream();
                request = requestReader.read(inputStream, requestMaxSize);

                if (request == null) {
                    return;
                }

            } catch (RequestTooLargeException e) {
                logger.warn(e.getMessage());

                Response response = new JsonResponse(
                        HttpStatus.REQUEST_TOO_LARGE,
                        Map.of("message", "Request Entity Too Large")
                );
                clientSocket.getOutputStream().write(response.getBytes());

                return;
            }

            try {
                Response response = dispatcher.dispatch(request);
                clientSocket.getOutputStream().write(response.getBytes());

            } catch (Exception e) {
                logger.error(
                        "Failed to handle: {} {}",
                        request.getMethod(),
                        request.getUrl(),
                        e
                );

                Response response = new JsonResponse(
                        HttpStatus.SERVER_ERROR,
                        Map.of("message", "Internal Server Error")
                );
                clientSocket.getOutputStream().write(response.getBytes());
            }

        } catch (IOException e) {
            logger.error("I/O error during client connection handling", e);
        }
    }
}
