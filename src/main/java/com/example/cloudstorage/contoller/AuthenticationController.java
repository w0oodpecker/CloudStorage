package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.model.AuthenticationRequestMail;
import com.example.cloudstorage.model.AuthenticationResponse;
import com.example.cloudstorage.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login") //Authorization method
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response;
        try {
            response = authenticationService.authentificate(request);
        }
        catch (Exception exc){
            return new ResponseEntity<>(AuthenticationRequestMail
                    .builder().email(request.getLogin())
                    .password(request.getPassword())
                    .build()
                    , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/logout") //Logout method
    public void logout() {}

}
