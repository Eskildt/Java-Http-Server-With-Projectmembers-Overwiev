package no.projectMembers.taskManager;

import no.projectMembers.http.HttpController;
import no.projectMembers.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberHttpController implements HttpController {
    private static final Logger logger = LoggerFactory.getLogger(MemberHttpController.class);

    private MemberDao memberDao;

    public MemberHttpController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException {
        try {
        if(requestAction.equals("POST")){
            query = HttpServer.parseQueryString(requestBody);
            Member member = new Member();
            member.setName(query.get("memberName"));
            memberDao.insert(member);
            return;
        }
            String statusCode = "200";
            String location = "text/html";
            String body = getBody();
            int contentLength = body.length();
            outputStream.write(("HTTP/1.1 " + statusCode + " OK\r\n" +
                    "Content-length: " + contentLength + "\r\n" +
                    "Connection: close\r\n" +
                    (location != null ? "Location: " + location + "\r\n" : "") +
                    "\r\n" +
                    body).getBytes());
        } catch (SQLException e) {
            logger.error("While handling request {}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.1 500 Internal server error\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: " + message.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    message).getBytes());
        }
    }

    String getBody() throws SQLException {
        String body = memberDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>%s</option>", p.getId()-1,p.getName()))
                .collect(Collectors.joining(""));
        return body;
    }
}


