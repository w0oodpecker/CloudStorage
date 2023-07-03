package com.example.cloudstorage.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@Component
public class CloudTools {

    private static int leftLimit;
    private static int rightLimit;
    private static int lenght;

    public CloudTools(@Value("${error.id.leftlimit}") int leftLimit,
                      @Value("${error.id.rightlimit}") int rightLimit,
                      @Value("${error.id.lenght}") int lenght) {
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.lenght = lenght;
    }

    /*Генератор случайной строки*/
    public static String getRandomCode() {
        Random random = new Random();
        String string = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(lenght)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return string;
    }

    @SneakyThrows
    public static String convertJsonToString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(object);
        return body;
    }

}
