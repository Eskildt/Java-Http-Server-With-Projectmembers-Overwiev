package no.projectMembers.taskManager;

import no.projectMembers.http.HttpServer;
import no.projectMembers.taskManager.assign.AssignController;
import no.projectMembers.taskManager.assign.AssignDao;
import no.projectMembers.taskManager.member.MemberController;
import no.projectMembers.taskManager.member.MemberDao;
import no.projectMembers.taskManager.projects.ProjectsDao;
import no.projectMembers.taskManager.projects.ProjectsHttpController;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;



public class MemberServer {

    private HttpServer server;


    public MemberServer(int port) throws IOException {

        Properties properties = new Properties();
        try(FileReader fileReader = new FileReader("task-manager.properties")){
            properties.load(fileReader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().clean();
        Flyway.configure().dataSource(dataSource).load().migrate();

        server = new HttpServer(port);
        server.setFileLocation("src/main/resources/");
        server.addController("/members", new MemberController(new MemberDao(dataSource)));
        server.addController("/projects", new ProjectsHttpController(new ProjectsDao(dataSource)));
        server.addController("/assignprojectmember", new AssignController(new AssignDao(dataSource)));

    }

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(MemberServer.class);

    public static void main(String[] args) throws IOException {
        new MemberServer(8080).start();
    }

    private void start(){
        server.start();
    }
}

