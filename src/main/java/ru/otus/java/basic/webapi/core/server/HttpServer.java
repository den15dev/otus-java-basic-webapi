package ru.otus.java.basic.webapi.core.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.webapi.core.ApplicationContext;
import ru.otus.java.basic.webapi.core.Config;
import ru.otus.java.basic.webapi.core.Dispatcher;
import ru.otus.java.basic.webapi.core.exception.RequestTooLargeException;
import ru.otus.java.basic.webapi.core.request.Request;
import ru.otus.java.basic.webapi.core.response.HttpStatus;
import ru.otus.java.basic.webapi.core.response.JsonResponse;
import ru.otus.java.basic.webapi.core.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private final int threadNum;
    private final int requestMaxSize;
    private final RequestParser requestParser;
    private final FileSender fileSender;
    private final Dispatcher dispatcher;
    private static final Logger logger = LogManager.getLogger(HttpServer.class);


    public HttpServer(Config config) {
        this.port = Integer.parseInt(config.get("server.port"));
        this.threadNum = Integer.parseInt(config.get("server.thread-pool-size"));
        this.requestMaxSize = Integer.parseInt(config.get("http.max-request-size-bytes"));

        this.requestParser = new RequestParser();
        this.fileSender = new FileSender();

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
            Response response;

            // Read request, check that it didn't exceed max size
            try {
                InputStream inputStream = clientSocket.getInputStream();
                request = requestParser.getRequest(inputStream, requestMaxSize);

                if (request == null) {
                    return;
                }

            } catch (RequestTooLargeException e) {
                logger.warn(e.getMessage());

                response = new JsonResponse(
                        HttpStatus.REQUEST_TOO_LARGE,
                        Map.of("message", "Request Entity Too Large")
                );
                clientSocket.getOutputStream().write(response.getBytes());

                return;
            }

            // Handle static files
            String filename = request.getUrl().substring(1);
            Path baseDir = Paths.get("static").toAbsolutePath();
            Path filePath = baseDir.resolve(filename).normalize();

            if (filePath.startsWith(baseDir) && Files.exists(filePath) && !Files.isDirectory(filePath)) {
                fileSender.send(filePath, socket.getOutputStream());

                return;
            }

            // Handle app request
            try {
                response = dispatcher.dispatch(request);
                clientSocket.getOutputStream().write(response.getBytes());

            } catch (Exception e) {
                logger.error(
                        "Failed to handle: {} {}",
                        request.getMethod(),
                        request.getUrl(),
                        e
                );

                response = new JsonResponse(
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
