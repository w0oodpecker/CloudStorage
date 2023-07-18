package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cloudstorage.dto.AuthenticationDto;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login") //Authorization method
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        AuthenticationDto.ResponseAuth.Create response;
        var request = new AuthenticationDto.RequestAuth.Create(authRequest.getLogin(), authRequest.getPassword());
        try {
            response = authenticationService.authentificate(request);
        }
        catch (Exception exc){
            //Адаптация к фронту вместо отправки объекта ошибки
            var resp = new ResponseEntity<>(new AuthenticationDto.ResponseAuthErr.Create(
                    authRequest.getLogin(), authRequest.getPassword())
                    , HttpStatus.BAD_REQUEST);
            return resp;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/logout") //Logout method
    public void logout() {}

}
