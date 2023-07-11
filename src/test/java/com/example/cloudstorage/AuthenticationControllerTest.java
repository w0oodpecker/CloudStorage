package com.example.cloudstorage;

import com.example.cloudstorage.contoller.AuthenticationController;
import com.example.cloudstorage.model.AuthenticationRequest;
import com.example.cloudstorage.model.AuthenticationResponse;
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

        AuthenticationResponse authResp = new AuthenticationResponse("token");
        when(mAuthService.authentificate(authReqOk)).thenReturn(authResp);
        when(mAuthService.authentificate(authReqErr)).thenThrow(RuntimeException.class);

        ResponseEntity<?> resp_ok = authController.login(authReqOk);
        ResponseEntity<?> resp_err = authController.login(authReqErr);

        Assertions.assertTrue(resp_ok.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp_err.getStatusCodeValue() == 400);
    }
}
