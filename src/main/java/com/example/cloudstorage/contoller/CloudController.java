package com.example.cloudstorage.contoller;

import com.example.cloudstorage.dto.CloudFileDto;
import com.example.cloudstorage.exceptions.*;
import com.example.cloudstorage.model.CloudError;
import com.example.cloudstorage.model.CloudFile;
import com.example.cloudstorage.service.CloudFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CloudController {

    private final CloudFileService fileService;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //Upload file to server
    public ResponseEntity<?> fileUploadCall(//@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName,
                                            @RequestParam("file") MultipartFile file) {
        try {
            var request = new CloudFileDto.RequestUploadFile.Create(fileName, file.getBytes());
            fileService.uploadFile(request);
        } catch (InputDataException | IOException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }


    @DeleteMapping("/file") //Delete file
    public ResponseEntity<?> deleteFileCall(//@RequestHeader("auth-token") String userAuthToken,
                                            @RequestParam("filename") String fileName) {
        try {
            var request = new CloudFileDto.RequestDeleteFile.Create(fileName);
            fileService.deleteFile(request);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (DeleteFileException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }


    @GetMapping("/file") //Download file from cloud
    public ResponseEntity<?> downloadFileCall(//@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String fileName) {
        CloudFileDto.ResponseDownloadFile.Create response;
        HttpHeaders headers;
        try {
            var request = new CloudFileDto.RequestDownloadFile.Create(fileName);
            response = fileService.downloadFile(request);
            MediaType mediaType = MediaType.MULTIPART_FORM_DATA;
            headers = new HttpHeaders();
            headers.setContentType(mediaType);
            ContentDisposition disposition = ContentDisposition.inline().filename(Objects
                    .requireNonNull(response.getFileResource().getFilename())).build();
            headers.setContentDisposition(disposition);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (RuntimeException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(response.getFileResource(), headers, HttpStatus.OK); //200
    }


    @PutMapping("/file") //Edit file name
    public ResponseEntity<?> editFileNameCall(//@RequestHeader("auth-token") String userAuthToken,
                                              @RequestParam("filename") String sourceFileName,
                                              @RequestBody CloudFile newFile) {
        try {
            var request = new CloudFileDto.RequestEditFile.Create(sourceFileName, newFile.getFilename());
            fileService.renameFile(request);
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (RenameFileException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(HttpStatus.OK); //200
    }


    @GetMapping("/list") //Get all files
    public ResponseEntity<?> listFilesCall(//@RequestHeader("auth-token") String userAuthToken,
                                           @RequestParam("limit") int limit) {
        CloudFileDto.ResponseGetFileList.Create response;
        try {
            response = fileService.getFileList();
        } catch (InputDataException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); //400
        } catch (GettingFileListException exc) {
            CloudError error = new CloudError(exc.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        return new ResponseEntity<>(response.getFileList(), HttpStatus.OK); //200
    }

}
