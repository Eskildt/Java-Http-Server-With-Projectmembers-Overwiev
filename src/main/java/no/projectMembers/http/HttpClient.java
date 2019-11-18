package no.projectMembers.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private final String hostname;
    private final int port;
    private String requestTarget;
    private String body;
    private Map<String, String> headers = new HashMap<>();




    public HttpClient(String hostname, int port, String requestTarget){

        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
    }

    public HttpClientResponse execute() throws IOException {
        Socket socket = new Socket(hostname, port);

        socket.getOutputStream().write(("GET " + requestTarget +
                " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                "Connection: close\r\n" +
                "\r\n").getBytes());

        return new HttpClientResponse(socket.getInputStream());
    }

    public void setRequestHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public void setBody(String body) {
        this.body = body;
    }
}
