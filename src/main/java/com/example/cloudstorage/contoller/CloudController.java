package com.example.cloudstorage.contoller;

import com.example.cloudstorage.exceptions.*;
import com.example.cloudstorage.model.CloudError;
import com.example.cloudstorage.model.CloudFile;
import com.example.cloudstorage.service.AuthenticationService;
import com.example.cloudstorage.service.CloudFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class CloudController {

    private final AuthenticationService authService;
    private final CloudFileService fileService;

    /*@PostMapping(value = "/login") //Authorization method
    public ResponseEntity<?> loginCall(@RequestBody JwtRequest authRequest) {
        // TODO: 6/27/2023 Уточнить формат ответа при ошибке аутентификации, сейчас отправляется объект ошибки, но фронт не ловит
        final JwtResponse token;
        try {
            token = authService.login(authRequest);
        } catch (AuthException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/logout") //Logout method
    public ResponseEntity<?> logoutCall(@RequestHeader("auth-token") String userAuthToken) {
        // TODO: 6/27/2023 Решить вопрос отзыва или блокировки токена
        return null;
    }

*/
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //Upload file to server
    public ResponseEntity<?> fileUploadCall(@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName,
                                            @RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFile(fileName, file);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (UnauthorizedException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }

    @DeleteMapping("/file") //Delete file
    public ResponseEntity<?> deleteFileCall(@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName) {
        try {
            fileService.deleteFile(fileName);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (UnauthorizedException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401
        } catch (DeleteFileException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }


    @GetMapping("/file") //Download file from cloud
    public ResponseEntity<?> downloadFileCall(@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String fileName) {
        FileSystemResource resource;
        HttpHeaders headers;
        try {
            resource = fileService.downloadFile(fileName);
            MediaType mediaType = MediaType.MULTIPART_FORM_DATA;
            headers = new HttpHeaders();
            headers.setContentType(mediaType);
            ContentDisposition disposition = ContentDisposition.inline().filename(resource.getFilename()).build();
            headers.setContentDisposition(disposition);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (UnauthorizedException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401
        } catch (RuntimeException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(resource, headers, HttpStatus.OK); //200
    }

    @PutMapping("/file") //Edit file name
    public ResponseEntity<?> editFileNameCall(@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String fileName,
                                              @RequestBody CloudFile newFile) {
        try {
            fileService.renameFile(fileName, newFile);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (UnauthorizedException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401
        } catch (RenameFileException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }


    @GetMapping("/list") //Get all files
    public ResponseEntity<?> listFilesCall(@RequestHeader("auth-token") String userAuthToken,
                                           @RequestParam("limit") int limit) {
        ArrayList<?> fileList;
        try {
            fileList = fileService.getFileList();
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (UnauthorizedException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); //401
        } catch (GettingFileListException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(fileList, HttpStatus.OK); //200
    }

}
