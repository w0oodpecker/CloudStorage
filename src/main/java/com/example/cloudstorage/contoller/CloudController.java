package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.Error;
import com.example.cloudstorage.model.JwtRequest;
import com.example.cloudstorage.model.JwtResponse;
import com.example.cloudstorage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@RestController
@RequiredArgsConstructor
public class CloudController {

    private final AuthService authService;

    @PostMapping("/login") //Authorization method
    public ResponseEntity<?> loginCall(@RequestBody JwtRequest authRequest) {
        final JwtResponse token;
        try {
            token = authService.login(authRequest);
        } catch (AuthException exc) {
            Error error = new Error(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout") //Logout method
    public ResponseEntity<?> logoutCall(@RequestHeader("auth-token") String userAuthToken) {
        return null;
    }

    /*@PostMapping("/file") //Upload file to server
    public ResponseEntity<?> fileUploadCall(@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName,
                                            @RequestBody UserFile userFile){
        return null;
    }*/

    @DeleteMapping("/file") //Delete file
    public ResponseEntity<?> deleteFileCall(@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName) {
        return null;
    }

    @GetMapping("/file") //Download file from cloud
    public ResponseEntity<?> downloadFileCall(@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String fileName ) {
        return null;
    }

    @PutMapping("/file") //Edit file name
    public ResponseEntity<?> editFileNameCall(@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String fileName,
                                              @RequestBody String name) {
        return null;
    }

    @GetMapping("/list") //Get all files
    public ResponseEntity<?> listFilesCall(@RequestHeader("auth-token") String userAuthToken,
                                           @RequestParam("limit") int limit) {
        return null;
    }
}
