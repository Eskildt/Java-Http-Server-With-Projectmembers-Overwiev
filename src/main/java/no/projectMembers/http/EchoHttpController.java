package no.projectMembers.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class EchoHttpController implements HttpController {
    @Override
    public void handle(String requestPath, Map<String, String> query, OutputStream outputStream) throws IOException {
        String statusCode = query.getOrDefault("status", "200");
        String location = query.getOrDefault("location", null);
        String body = query.getOrDefault("body", "Hello World!");

        outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" + "Content-length: " + body.length() + "\r\n" + "Connection: close\r\n" +
                (location != null ? "Location: " + location + "\r\n" : "") + "\r\n" + body).getBytes());
    }
}
