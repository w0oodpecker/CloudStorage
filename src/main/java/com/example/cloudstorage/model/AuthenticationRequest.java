package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Builder
public class AuthenticationRequest { //User credentials scheme
    private String login;
    private String password;

    @JsonCreator
    public AuthenticationRequest(@JsonProperty("login") String login, @JsonProperty("password") String password){
        this.login = login;
        this.password = password;
    }

}
