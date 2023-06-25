package com.example.cloudstorage.model;

import com.example.cloudstorage.component.Tools;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;


@Getter
@Setter
public class Error {

    private Integer id;
    private String message;

    public Error(String message) {
        this.id = Integer.parseInt(Tools.getRandomCode());
        this.message = message;
    }
}
