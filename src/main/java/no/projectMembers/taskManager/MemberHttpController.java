package no.projectMembers.taskManager;

import com.google.gson.Gson;
import no.projectMembers.http.HttpController;
import no.projectMembers.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

import static no.projectMembers.http.HttpServer.decodeValue;

public class MemberHttpController implements HttpController {

    private final MemberDao memberDao;

    public MemberHttpController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException{


        try {
            if (requestAction.equals("PUT")){
                System.out.println(requestBody);
                query = HttpServer.parseQueryString(requestBody);

                Member member = new Member();
                    System.out.println(query.get("name"));

                    member.setName(decodeValue(query.get("name")));
                    member.setEmail(decodeValue(query.get("email")));
                    member.setProject(decodeValue(query.get("project")));
                    memberDao.insert(member);
                        outputStream.write(("HTTP/1.1 200 OK\r\n" +
                            "Connection: close\r\n" +
                                "\r\n").getBytes());
                    return;
                }
                    if (requestAction.equals("POST")){
                System.out.println(requestBody);
                query = HttpServer.parseQueryString(requestBody);

                Member member = new Member();
                System.out.println(query.get("name"));
                member.setName(decodeValue(query.get("name")));
                member.setEmail(decodeValue(query.get("email")));
                member.setProject(decodeValue(query.get("project")));
                memberDao.alter(member);
                outputStream.write(("HTTP/1.1 200 OK\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;
            }

            int status = 200;
            String body = getBody();
            int contentLength = body.getBytes("UTF-8").length;
            String contentType = "application/json";
            outputStream.write(("HTTP/1.0 200 OK\r\n" + "Content-length: " + contentLength +  "\r\n" +
                    "Content-type: " + contentType + "\r\n" + "Connection: close\r\n"+ "\r\n" + body).getBytes("UTF-8"));
            outputStream.flush();
        } catch (SQLException e) {
            logger.error("While handling requests {}", requestPath, e);
            String message = e.toString();
            outputStream.write(("HTTP/1.1 500 Internal server error\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-Length: " + message.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" ).getBytes());
        }
    }

    public String getBody() throws SQLException {
        var members= memberDao.listAll();

        return new Gson().toJson(members);
    }

}



