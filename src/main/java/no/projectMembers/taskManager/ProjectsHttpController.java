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

public class ProjectsHttpController implements HttpController{

        private final ProjectsDao projectsDao;

        public ProjectsHttpController(ProjectsDao projectsDao) {
            this.projectsDao = projectsDao;
        }

        private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

        @Override
        public void handle(String requestAction, String requestPath, Map<String, String> query, String requestBody, OutputStream outputStream) throws IOException {
            logger.info(requestAction);
            try {

                if (requestAction.equals("POST")){
                    query = HttpServer.parseQueryString(requestBody);
                    Projects projects = new Projects();

                    System.out.println(requestBody);

                    String tmpName = Decoder.decodeValue(query.get("projectName"));
                    String tmpStatus = Decoder.decodeValue(query.get("status"));

                    projects.setName(tmpName);
                    projects.setStatus(tmpStatus);
                    projectsDao.insert(projects);
                    outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                            "Location: http://localhost:8080/newProject.html\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes());
                    return;
                }


                String status = "200";
                String contentType = "text/html";
                String body = getBody();
                int contentLength = body.getBytes("UTF-8").length;
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
            String body = projectsDao.listAll().stream()
                    .map(p -> String.format("<option id='%s'>%s | Status: %s</option>", p.getId(), p.getName(), p.getStatus()))
                    .collect(Collectors.joining(""));
            return body;
        }

    }