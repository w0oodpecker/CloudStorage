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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CloudControllerTest {

    @Test
    public void testFileUploadCall() throws InputDataException, IOException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        MockMultipartFile mockMultipartFileOk = new MockMultipartFile("data", "filenameOk.txt", "text/plain", "some xml".getBytes());
        var requestOk = new CloudFileDto.RequestUploadFile.Ask("filenameOk.200", mockMultipartFileOk.getBytes());
        doNothing().when(mCloudFileService).uploadFile(requestOk);
        ResponseEntity<?> resp200 = cloudController.fileUploadCall("filenameOk.200", mockMultipartFileOk);
        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);

        MockMultipartFile mockMultipartFileErr = new MockMultipartFile("data", "filenameErr.txt", "text/plain", "some txt".getBytes());
        var requestErr = new CloudFileDto.RequestUploadFile.Ask("filenameErr.400", mockMultipartFileErr.getBytes());
        doThrow(new InputDataException("400")).when(mCloudFileService).uploadFile(requestErr);
        ResponseEntity<?> resp400 = cloudController.fileUploadCall("filenameErr.400", mockMultipartFileErr);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
    }


    @Test
    public void testDeleteFileCall() throws DeleteFileException, InputDataException {
        CloudFileService mCloudFileService = Mockito.mock(CloudFileService.class);
        CloudController cloudController = new CloudController(mCloudFileService);

        doThrow(new InputDataException("400")).when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Ask("filenameErr.400"));
        doThrow(new DeleteFileException("500")).when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Ask("filenameErr.500"));
        doNothing().when(mCloudFileService).deleteFile(new CloudFileDto.RequestDeleteFile.Ask("filenameOk.200"));

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

        CloudFileDto.RequestDownloadFile.Ask request400 = new CloudFileDto.RequestDownloadFile.Ask("filename.400");
        CloudFileDto.RequestDownloadFile.Ask request500 = new CloudFileDto.RequestDownloadFile.Ask("filename.500");

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

        doThrow(new InputDataException("400")).when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Ask("filename.400", cloudFile.getFilename()));
        doThrow(new RenameFileException("500")).when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Ask("filename.500", cloudFile.getFilename()));
        doNothing().when(mCloudFileService).renameFile(new CloudFileDto.RequestEditFile.Ask("filename.200", cloudFile.getFilename()));

        ResponseEntity<?> resp200 = cloudController.editFileNameCall("filename.200", cloudFile);
        ResponseEntity<?> resp400 = cloudController.editFileNameCall("filename.400", cloudFile);
        ResponseEntity<?> resp500 = cloudController.editFileNameCall("filename.500", cloudFile);

        Assertions.assertTrue(resp200.getStatusCodeValue() == 200);
        Assertions.assertTrue(resp400.getStatusCodeValue() == 400);
        Assertions.assertTrue(resp500.getStatusCodeValue() == 500);
    }
}
