package no.projectMembers.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

class FileHttpController implements HttpController {
    private HttpServer httpServer;

    public FileHttpController(HttpServer httpServer) { this.httpServer = httpServer; }

    @Override
    public void handle(String requestPath, Map<String, String> query, OutputStream outputStream) throws IOException {
        File file = new File(httpServer.getFileLocation() + requestPath);
        if(file.isFile()) {
            outputStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + file.length() + "\r\n"
                    + "Connection: close\r\n" +
                    "\r\n").getBytes());

            new FileInputStream(file).transferTo(outputStream);
        } else {
            outputStream.write(("HTTP/1.1 404 NOT FOUND\r\n").getBytes());
        }

    }
}
