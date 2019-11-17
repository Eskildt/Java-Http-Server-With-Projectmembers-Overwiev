package no.projectMembers.taskManager;

import no.projectMembers.http.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

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
        server.setAssetRoot("src/main/resources/");
        server.addController("/members", new MemberHttpController(new MemberDao(dataSource)));
        server.addController("/projects", new ProjectsHttpController(new ProjectsDao(dataSource)));

    }

    public static void main(String[] args) throws IOException {
        new MemberServer(8080).start();
    }

    private void start(){
        server.start();
    }
}

