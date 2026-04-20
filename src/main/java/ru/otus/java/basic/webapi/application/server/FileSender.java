package ru.otus.java.basic.webapi.application.server;

import ru.otus.java.basic.webapi.application.response.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileSender {
    public void send(Path filePath, OutputStream outputStream) throws IOException {
        long fileSize = Files.size(filePath);

        Response response = new Response();
        response.addHeader("Content-Length", String.valueOf(fileSize));
        response.addHeader("Content-Type", detectContentType(filePath));

        outputStream.write(response.getBytes());
        outputStream.flush();

        try (
                FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ);
                WritableByteChannel socketChannel = Channels.newChannel(outputStream)
        ) {
            long position = 0;

            while (position < fileSize) {
                long transferred = fileChannel.transferTo(position, fileSize - position, socketChannel);

                if (transferred == 0) {
                    break;
                }

                position += transferred;
            }
        }
    }


    private String detectContentType(Path filePath) throws IOException {
        String contentType = Files.probeContentType(filePath);
        return contentType != null ? contentType : "application/octet-stream";
    }
}
