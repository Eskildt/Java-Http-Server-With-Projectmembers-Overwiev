package no.projectMembers.taskManager;

import no.projectMembers.http.HttpController;
import no.projectMembers.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;


public class MemberHttpController implements HttpController {

    private final MemberDao memberDao;

    public MemberHttpController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException{


        try {
            if (requestAction.equals("POST")){
                query = HttpServer.parseQueryString(requestBody);
                 Member member = new Member();

                String tmpName = Decoder.decodeValue(query.get("memberName"));

                member.setName(tmpName);
                memberDao.insert(member);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/newWorker.html\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;
            }

            String status = "200";
            String contentType = "text/html";
            String body = getBody();
            int contentLength = body.getBytes(StandardCharsets.UTF_8).length;
            outputStream.write(("HTTP/1.1 " + status + " OK\r\n" +
                    "content-type: " + contentType + "\r\n" +
                    "content-length: " + contentLength + "\r\n" +
                    "connection: close\r\n" + "\r\n" +
                    body).getBytes(StandardCharsets.UTF_8));
        } catch (SQLException e) {
            logger.error("While handling request {}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.1 500 Internal server error\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: " + message.length() + "\r\n" +
                    "Connection: close\r\n" + "\r\n" +
                    message).getBytes());
        }
    }


    public String getBody() throws SQLException {
        String body = memberDao.listAll().stream()
                .map(p -> String.format("<option id='%s'>%s</option>", p.getId(), p.getName()))
                .collect(Collectors.joining(""));
        return body;
    }

}



