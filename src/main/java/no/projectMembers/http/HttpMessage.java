package no.projectMembers.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
        /*
        startLine = readLine(inputStream);
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerName = headerLine.substring(0, colonPos).trim();
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headers.put(headerName.toLowerCase(), headerValue);
        }

        if (getHeader("Content-Length") != null) {
            this.body = readBytes(inputStream, getContentLength());
        }

    }
         */


            static String readLine(InputStream inputStream) throws IOException {
                StringBuilder statusLine = new StringBuilder();
                int c;
                while ((c = inputStream.read()) != -1) {
                    if (c == '\r') {
                        inputStream.read();
                        break;
                    }
                    statusLine.append((char) c);
                }
                return statusLine.toString();
            }



    static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = readLine(inputStream)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            headers.put(headerLine.substring(0, colonPos).trim().toLowerCase(),
                    headerLine.substring(colonPos + 1).trim());
        }
        return headers;
            }

    static String readBody(Map<String, String> headers, InputStream inputStream) throws IOException {
        if(headers.containsKey("Content-length")){
            StringBuilder body = new StringBuilder();
            for (int i = 0; i< Integer.parseInt(headers.get("Content-length")); i++){
                body.append((char)inputStream.read());
            }
            return body.toString();
        }else {
            return null;
        }
    }

    /*
    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public static String readLine(InputStream inputStream) throws IOException {

        StringBuilder line = new StringBuilder();

        int c;
        while ((c = inputStream.read()) != -1) {
            if (c == '\r') {
                //noinspection ResultOfMethodCallIgnored
                inputStream.read(); // the follow \n
                break;
            }

            line.append((char)c);
        }

        return line.toString();

    }

    protected String readBytes(InputStream inputStream, int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            body.append((char)inputStream.read());
        }
        return body.toString();
    }

    public String getStartLine() {
        return startLine;
    }
     */


}