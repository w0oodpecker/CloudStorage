package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/cloud")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(value = "/login") //Authorization method
    public void login() {
    }


    @PostMapping("/logout") //Logout method
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String userAuthToken) {
        // TODO: 6/27/2023 Решить вопрос отзыва или блокировки токена
        return null;
    }


}
