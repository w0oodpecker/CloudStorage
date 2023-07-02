package com.example.cloudstorage.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping(value = "/login") //Authorization method
    public void login() {}


    @PostMapping(value = "/logout") //Logout method
    public void logout() {}

}
