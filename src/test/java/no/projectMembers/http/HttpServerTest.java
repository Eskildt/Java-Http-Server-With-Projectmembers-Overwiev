package no.projectMembers.http;

import java.io.IOException;

public class HttpServerTest {

        private HttpServer server;
        /*

        @BeforeEach
        void setUp() throws IOException {
            server = new HttpServer(0);
            server.start();
        }

        @Test
        void shouldReturnStatusCode200() throws IOException {
            HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
            assertEquals(200, client.execute().getStatusCode());
        }

        @Test
        void shouldReturnStatusCode401() throws IOException {
            HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=401");
            assertEquals(401, client.execute().getStatusCode());
        }

        @Test
        void shouldReturnHeaders() throws IOException {
            HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?status=302&location=http://www.example.com");
            HttpClientResponse response = client.execute();
            assertEquals(302, response.getStatusCode());
            assertEquals("http://www.example.com", response.getHeader("location"));
        }

        @Test
        void shouldReturnBody() throws IOException {
            HttpClient client = new HttpClient("localhost", server.getPort(), "/echo?body=HelloWorld!");
            assertEquals("Hello World!", client.execute().getBody());
        }

        @Test
        void shouldReturnFileFromDisk() throws IOException {
            Files.writeString(Paths.get("target/mytestfile.txt"), "Hello Kristiania");
            server.setFileLocation("target");
            HttpClient httpClient = new HttpClient("localhost", server.getPort(),"/mytestfile.txt");
            HttpClientResponse response = httpClient.execute();
            assertEquals("Hello Kristiania", response.getBody());
        }

        @Test
        void shouldDefaultToIndexHtml() throws IOException {
            server.setFileLocation("target/");
            String fileContent = "<body>Some random string " + System.currentTimeMillis() + "</body>";
            Files.writeString(Paths.get("target", "index.html"), fileContent);

            HttpClientResponse response = executeLocalRequest("/");
            assertThat(response.getBody()).isEqualTo(fileContent);
        }

         */


        private HttpClientResponse executeLocalRequest(String s) throws IOException {
            HttpClient client = new HttpClient("localhost", server.getPort(), s);
            return client.execute();
        }
}
