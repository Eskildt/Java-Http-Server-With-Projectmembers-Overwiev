package no.projectMembers.taskManager.assign;

import no.projectMembers.http.HttpController;
import no.projectMembers.http.HttpServer;
import no.projectMembers.taskManager.Decoder;
import no.projectMembers.taskManager.member.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class AssignController implements HttpController {


    private AssignDao assignDao;

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    public AssignController(AssignDao assignDao) {
        this.assignDao = assignDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException {

        try {
            if (requestAction.equals("POST")){
                query = HttpServer.parseQueryString(requestBody);
                AssignMember assignMember = new AssignMember();

                String tmpProject = Decoder.decodeValue(query.get("projectid"));
                String tmpMember = Decoder.decodeValue(query.get("memberid"));

                assignMember.setProject(tmpProject);
                assignMember.setMember(tmpMember);
                assignDao.insert(assignMember);
                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/assignMemberToProject.html\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;
            }

            String status = "200";
            String contentType = "text/html";
            String body = getBody();
            int contentLength = body.getBytes().length;
            outputStream.write(("HTTP/1.1 " + status + " OK\r\n" +
                    "content-type: " + contentType + "\r\n" +
                    "content-length: " + contentLength + "\r\n" +
                    "connection: close\r\n" + "\r\n" +
                    body).getBytes());
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
        String body = assignDao.listAll().stream()
                .map(p -> String.format("<option value='%s'>Member: %s  |  Task: %s</option>", p.getId(), p.getMember(), p.getProject()))
                .collect(Collectors.joining(""));
        return body;
    }
}
