package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JWTRequest { //User credentials scheme
    private String login;
    private String password;

    @JsonCreator
    public JWTRequest(@JsonProperty("login") String login, @JsonProperty("password") String password){
        this.login = login;
        this.password = password;
    }
}
