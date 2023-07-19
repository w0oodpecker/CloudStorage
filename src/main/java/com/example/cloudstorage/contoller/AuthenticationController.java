package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.service.AuthenticationService;
import static com.example.cloudstorage.configuration.CloudMessages.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cloudstorage.dto.AuthenticationDto;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login") //Authorization method
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        AuthenticationDto.ResponseAuth.Answer response;
        var request = new AuthenticationDto.RequestAuth.Ask(authRequest.getLogin(), authRequest.getPassword());
        try {
            response = authenticationService.authentificate(request);
        }
        catch (Exception exc){
            var resp = new ResponseEntity<>(new AuthenticationDto.ResponseAuthErr.Answer(
                    authRequest.getLogin(), authRequest.getPassword())
                    , HttpStatus.BAD_REQUEST);
            return resp;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/logout") //Logout method
    public void logout() {}

}
