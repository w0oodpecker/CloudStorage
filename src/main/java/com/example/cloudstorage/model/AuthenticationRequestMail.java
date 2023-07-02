package com.example.cloudstorage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//Для возврата во фронт вместо ошибки при некорректных логине или пароля
@Getter
@Setter
@Data
@Builder
public class AuthenticationRequestMail { //User credentials scheme
    @JsonProperty("email")
    private String email;
    private String password;

    @JsonCreator
    public AuthenticationRequestMail(String login, String password){
        this.email = login;
        this.password = password;
    }
}
