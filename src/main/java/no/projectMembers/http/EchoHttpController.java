package no.projectMembers.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class EchoHttpController implements HttpController {
    @Override
    public void handle(String requestAction, String path, Map<String, String> query, String body, OutputStream outputStream) throws IOException {
        if(requestAction.equals("POST")){
            query = HttpServer.parseQueryString(body);
        }
        String statusCode = query.getOrDefault("status", "200");
        String contentType = query.getOrDefault("content-type", "text/plain");
        String responseBody = query.getOrDefault("body", "Hello World!");
        int contentLength = responseBody.length();
        outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                "Content-type: " + contentType + "\r\n" +
                "Content-length: " + contentLength + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseBody).getBytes());
    }
}
