package com.example.cloudstorage;

import com.example.cloudstorage.contoller.AuthenticationController;
import com.example.cloudstorage.dto.AuthenticationDto;
import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Test
    public void testLogin() {
        AuthenticationService mAuthService = Mockito.mock(AuthenticationService.class);
        AuthenticationController authController = new AuthenticationController(mAuthService);

        AuthenticationRequest authReqOk = new AuthenticationRequest("ivanov_ok", "111111");
        AuthenticationRequest authReqErr = new AuthenticationRequest("petrov_err", "222222");

        AuthenticationDto.ResponseAuth.Answer authResp = new AuthenticationDto.ResponseAuth.Answer("token");
        when(mAuthService.authentificate(new AuthenticationDto.RequestAuth.Ask(authReqOk.getLogin(), authReqOk.getPassword())))
                .thenReturn(authResp);
        when(mAuthService.authentificate(new AuthenticationDto.RequestAuth.Ask(authReqErr.getLogin(), authReqErr.getPassword())))
                .thenThrow(RuntimeException.class);

        ResponseEntity<?> resp200 = authController.login(authReqOk);
        ResponseEntity<?> resp400 = authController.login(authReqErr);

        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
    }
}
