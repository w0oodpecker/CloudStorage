package com.example.cloudstorage.contoller;

import com.example.cloudstorage.model.JWTRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CloudController {


    @PostMapping("/login") //Authorization method
    public String loginCall(@RequestBody JWTRequest userCredentials) {
        return null;
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
