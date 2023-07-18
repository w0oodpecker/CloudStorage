package com.example.cloudstorage.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class CloudTools {

    @Value("${error.lenght}")
    public static int lenght;

    /*Генератор случайной строки*/
    public static String getRandomCode() {
        Random random = new Random();
        return random.ints(48, 57 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(lenght)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @SneakyThrows
    public static String convertJsonToString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static void generateBody(HttpServletResponse response, Object object) throws IOException {
        PrintWriter out = response.getWriter();
        String body = convertJsonToString(object);
        out.println(body);
        out.flush();
    }

}
