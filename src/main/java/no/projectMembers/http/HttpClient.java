package no.projectMembers.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        setRequestHeader("Host", hostname);
        setRequestHeader("Connection", "close");
    }

    public static void main(String[] args) throws IOException {
        new HttpClient("urlecho.appspot.com", 80, "/echo?body=Hello+World!").execute();
    }

    public HttpClientResponse execute() throws IOException {
        return execute("GET");
    }

    public HttpClientResponse execute(final String httpMethod) throws IOException {
        Socket socket = new Socket(hostname, port);

        if (body != null){
            setRequestHeader("content-length", String.valueOf(body.length()));
        }

        String headerString = headers.entrySet().stream()
                .map(e-> e.getKey() + " : " + e.getValue())
                .collect(Collectors.joining("\r\n"));


        socket.getOutputStream().write((httpMethod + " " + requestTarget + " HTTP/1.1\r\n" +
                headerString +
                "\r\n\r\n" + body).getBytes());
        return new HttpClientResponse(socket.getInputStream());

    }

    public void setRequestHeader(String headerName, String headerValue){
        headers.put(headerName, headerValue);
    }
    public void setBody(String body){
        this.body = body;
    }

}
