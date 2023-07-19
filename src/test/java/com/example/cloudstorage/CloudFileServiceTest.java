package com.example.cloudstorage;

import com.example.cloudstorage.dto.CloudFileDto;
import com.example.cloudstorage.exceptions.InputDataException;
import com.example.cloudstorage.service.CloudFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class CloudFileServiceTest {

    @Test
    public void testGetFileList() {
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").getFileList());
        Assertions.assertThrows(InputDataException.class, () -> new CloudFileService("").getFileList());
    }

    @Test
    public void testDeleteFile() throws IOException {
        File testFile = new File("src/test/TestFolder/testfile.txt");
        testFile.createNewFile();
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").deleteFile(new CloudFileDto.RequestDeleteFile.Ask("testfile.txt")));
        Assertions.assertThrows(InputDataException.class, () -> new CloudFileService("src/test/TestFolder").
                deleteFile(new CloudFileDto.RequestDeleteFile.Ask("error.txt")));
    }

    @Test
    public void testUploadFile(){
        MultipartFile file = new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes());
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder")
                .uploadFile(new CloudFileDto.RequestUploadFile.Ask("uploadtest.txt", file.getBytes())));
    }

    @Test
    public void testDownloadFile(){
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder")
                .downloadFile(new CloudFileDto.RequestDownloadFile.Ask("downloadtest.txt")));
    }

    @Test
    public void testRenameFile(){
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").
                renameFile(new CloudFileDto.RequestEditFile.Ask("renamefiletest.txt", "renamefiletest.txt")));

    }


}
