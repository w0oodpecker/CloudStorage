package com.example.cloudstorage;

import com.example.cloudstorage.contoller.CloudController;
import com.example.cloudstorage.dto.CloudFileDto;
import com.example.cloudstorage.exceptions.DeleteFileException;
import com.example.cloudstorage.exceptions.InputDataException;
import com.example.cloudstorage.exceptions.RenameFileException;
import com.example.cloudstorage.model.CloudFile;
import com.example.cloudstorage.service.CloudFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CloudControllerTest {

    @Test
    public void testFileUploadCall() throws InputDataException, IOException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        var requestOk = new CloudFileDto.RequestUploadFile.Create("filenameOk.200", mockMultipartFile.getBytes());
        var requestErr = new CloudFileDto.RequestUploadFile.Create("filenameOk.400", mockMultipartFile.getBytes());

        doNothing().when(mCloudFileService).uploadFile(requestOk);
        doThrow(new InputDataException("400")).when(mCloudFileService).uploadFile(requestErr);

        ResponseEntity<?> resp200 = cloudController.fileUploadCall("filenameOk.200", mockMultipartFile);
        ResponseEntity<?> resp400 = cloudController.fileUploadCall("filenameErr.400", mockMultipartFile);

        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
    }


    @Test
    public void testDeleteFileCall() throws DeleteFileException, InputDataException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        doThrow(new InputDataException("400")).when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Create("filenameErr.400"));
        doThrow(new DeleteFileException("500")).when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Create("filenameErr.500"));
        doNothing().when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Create("filenameOk.200"));

        ResponseEntity<?> resp400 = cloudController.deleteFileCall("filenameErr.400");
        ResponseEntity<?> resp500 = cloudController.deleteFileCall("filenameErr.500");
        ResponseEntity<?> resp200 = cloudController.deleteFileCall("filenameOk.200");

        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
        Assertions.assertTrue(resp500.getStatusCodeValue() == 500);
    }


    @Test
    public void testDownloadFileCall() throws InputDataException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        CloudFileDto.RequestDownloadFile.Create request400 = new CloudFileDto.RequestDownloadFile.Create("filename.400");
        CloudFileDto.RequestDownloadFile.Create request500 = new CloudFileDto.RequestDownloadFile.Create("filename.500");

        doThrow(new InputDataException("400")).when(mCloudFileService).downloadFile(request400);
        doThrow(new RuntimeException("500")).when(mCloudFileService).downloadFile(request500);

        ResponseEntity<?> resp400 = cloudController.downloadFileCall("filename.400");
        ResponseEntity<?> resp500 = cloudController.downloadFileCall("filename.500");

        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
        Assertions.assertTrue(resp500.getStatusCodeValue() == 500);
    }


    @Test
    public void testEditFileNameCall() throws InputDataException, RenameFileException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        CloudFile cloudFile = new CloudFile("newfile.txt", 300);

        doThrow(new InputDataException("400")).when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Create("filename.400", cloudFile.getFilename()));
        doThrow(new RenameFileException("500")).when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Create("filename.500", cloudFile.getFilename()));
        doNothing().when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Create("filename.200", cloudFile.getFilename()));

        ResponseEntity<?> resp200 = cloudController.editFileNameCall("filename.200", cloudFile);
        ResponseEntity<?> resp400 = cloudController.editFileNameCall("filename.400", cloudFile);
        ResponseEntity<?> resp500 = cloudController.editFileNameCall("filename.500", cloudFile);

        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
        Assertions.assertTrue(resp500.getStatusCodeValue() == 500);
    }
}
