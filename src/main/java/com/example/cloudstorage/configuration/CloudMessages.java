package com.example.cloudstorage.configuration;

import org.springframework.stereotype.Component;

@Component
public class CloudMessages {

    public static final String AUTHORIZATION = "Auth-Token";
    public static final String BADLOGIN = "Пользователь не найден";
    public static final String BADPASSWORD = "Пароль отстой";
    public static final String USERUNOUTHORIZED = "Пользователь не авторизован";

}
