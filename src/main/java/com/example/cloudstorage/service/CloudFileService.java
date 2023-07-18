package com.example.cloudstorage.service;

import com.example.cloudstorage.exceptions.*;
import com.example.cloudstorage.model.CloudFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static com.example.cloudstorage.configuration.CloudMessages.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudFileService {

    private final String filesPath;

    public CloudFileService(@Value("${files.path}") String filesPath) {
        this.filesPath = filesPath;
    }

    public ArrayList<?> getFileList() throws InputDataException, GettingFileListException { //Получение списка файлов репозитория
        File dir = new File(filesPath);
        if (!dir.exists()) throw new InputDataException(FOLDERNOTFOUND);
        List<CloudFile> lst = new ArrayList<>();
        try {
            for (File file : dir.listFiles()) {
                if (file.isFile())
                    lst.add(new CloudFile(file.getName(), file.length()));
            }
        } catch (NullPointerException exc) {
            throw new GettingFileListException(CLOUDSTOREERROR);
        }
        return (ArrayList<CloudFile>) lst;
    }

    public void deleteFile(String fileName) throws InputDataException, DeleteFileException { //Удаление файла
        File file = new File(filesPath + "/" + fileName);
        if (!file.exists() || !file.isFile())
            throw new InputDataException(FILENOTFOUND);
        if (!file.delete()) {
            throw new DeleteFileException(FILEDELETEERROR);
        }
    }

    public void uploadFile(String fileName, /*MultipartFile file*/ byte[] bytes) throws InputDataException { //Загрузка файла
        try {
            //byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filesPath + "/" + fileName)));
            stream.write(bytes);
            stream.close();
        } catch (IOException exc) {
            throw new InputDataException(FILECREATIONERROR);
        }
    }

    public FileSystemResource downloadFile(String fileName) throws InputDataException { //Запрос файла
        FileSystemResource resource;
        resource = new FileSystemResource(filesPath + "/" + fileName);
        if (!resource.exists() | !resource.isFile() | !resource.isReadable()) {
            throw new InputDataException(FILEREADERROR);
        }
        return resource;
    }

    public void renameFile(String fileName, CloudFile newFile) throws InputDataException, RenameFileException {
        File sourcFile = new File(filesPath + "/" + fileName);
        if (!sourcFile.exists() | sourcFile.isDirectory()) {
            throw new InputDataException(FILENOTFOUND);
        }
        if (!sourcFile.renameTo(new File(filesPath + "/" + newFile.getFilename()))) {
            throw new RenameFileException(FILERENAMEERROR);
        }
    }
}
