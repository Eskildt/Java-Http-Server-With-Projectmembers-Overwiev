package no.projectMembers.taskManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Decoder {
    public static String decodeValue(String value){
        try{
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        }catch (UnsupportedEncodingException ex){
            throw new RuntimeException(ex.getCause());
        }
    }
}
