package no.projectMembers.taskManager;

import no.projectMembers.http.HttpServer;

import java.io.IOException;

public class MemberServer {
        public static void main(String[] args) throws IOException{
            HttpServer server = new HttpServer(8080);
            server.setFileLocation("src/main/resources/no.projectMembers.taskManager");
            //server.addController("/api.products", new ProductHttpController());
            server.start();
        }
    }

