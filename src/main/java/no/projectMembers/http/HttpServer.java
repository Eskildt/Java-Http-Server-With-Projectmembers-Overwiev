package no.projectMembers.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static no.projectMembers.http.HttpMessage.readHeaders;

public class HttpServer {

    private static final Logger Logger = LoggerFactory.getLogger(HttpServer.class);


    private ServerSocket serverSocket;
    private String assetRoot;

    private HttpController defaultController = new FileHttpController(this);

    private Map<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        controllers.put("/echo", new EchoHttpController());
    }

    public void start() {
        new Thread(() -> run()).start();
        Logger.info("Started on http//:localhost:{}", getPort());
    }

    public void run() {

        System.out.println("Server running on localhost: " + getPort());
        boolean keepRunning = true;

        while (keepRunning) {

            try {
                Socket socket = serverSocket.accept();

                String requestLine = HttpMessage.readLine(socket.getInputStream());
                if (requestLine.isBlank()) continue;

                Logger.debug("Handling request: {}", requestLine);

                Map<String, String> headers = readHeaders(socket.getInputStream());
                String body = HttpMessage.readBody(headers, socket.getInputStream());
                String requestAction = requestLine.split(" ")[0];
                String requestTarget = requestLine.split(" ")[1];

                int questionPos = requestTarget.indexOf('?');
                String requestPath = questionPos == -1 ? requestTarget : requestTarget.substring(0, questionPos);
                System.out.println(requestTarget);
                Map<String, String> query = getQueryParameters(requestTarget);

                controllers.getOrDefault(requestPath, defaultController)
                        .handle(requestAction, requestPath, query, body, socket.getOutputStream());

                if(requestPath.equals("/stop")){
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Map<String, String> getQueryParameters(String requestTarget) {
        int questionPos = requestTarget.indexOf('?');
        if (questionPos != -1){
            String query = requestTarget.substring(questionPos+1);
            return parseQueryString(query);
        }
        return new HashMap<>();
    }


    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = new HashMap<>();
        for (String parameter : queryString.split("&")) {

            int equalsPos = parameter.indexOf('=');
            String parameterName = parameter.substring(0, equalsPos);
            String parameterValue = parameter.substring(equalsPos + 1);

            parameters.put(parameterName, parameterValue);
        }
        return parameters;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setAssetRoot(String assetRoot) {
        this.assetRoot = assetRoot;
    }

    public String getAssetRoot() {
        return assetRoot;
    }

    public void addController(String requestPath, HttpController controller) {
        controllers.put(requestPath, controller);
    }
}