package com.example.cloudstorage;

import com.example.cloudstorage.exceptions.GettingFileListException;
import com.example.cloudstorage.exceptions.InputDataException;
import com.example.cloudstorage.model.CloudFile;
import com.example.cloudstorage.service.CloudFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CloudFileServiceTest {

    @Test
    public void testGetFileList() throws GettingFileListException, InputDataException {
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").getFileList());
        Assertions.assertThrows(InputDataException.class, () -> new CloudFileService("").getFileList());
    }

    @Test
    public void testDeleteFile() throws IOException {
        File testFile = new File("src/test/TestFolder/testfile.txt");
        testFile.createNewFile();
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").deleteFile("testfile.txt"));
        Assertions.assertThrows(InputDataException.class, () -> new CloudFileService("src/test/TestFolder").deleteFile("error.txt"));
    }

    @Test
    public void testUploadFile(){
        MultipartFile file = new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes());
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").uploadFile("uploadtest.txt", file.getBytes()));
    }

    @Test
    public void testDownloadFile(){
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").downloadFile("downloadtest.txt"));
    }

    @Test
    public void testRenameFile(){
        Assertions.assertDoesNotThrow(() -> new CloudFileService("src/test/TestFolder").renameFile("renamefiletest.txt",
                new CloudFile("renamefiletest.txt", 0)));

    }


}
