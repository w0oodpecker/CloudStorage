package com.example.cloudstorage.service;

import com.example.cloudstorage.dto.CloudFileDto;
import com.example.cloudstorage.exceptions.*;
import com.example.cloudstorage.model.CloudFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import static com.example.cloudstorage.configuration.CloudMessages.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CloudFileService {

    private final String filesPath;

    public CloudFileService(@Value("${files.path}") String filesPath) {
        this.filesPath = filesPath;
    }

    public CloudFileDto.ResponseGetFileList.Answer getFileList() throws InputDataException, GettingFileListException { //Получение списка файлов репозитория
        File dir = new File(filesPath);
        if (!dir.exists()) throw new InputDataException(FOLDERNOTFOUND);
        List<CloudFile> lst = new ArrayList<>();
        try {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile())
                    lst.add(new CloudFile(file.getName(), file.length()));
            }
        } catch (NullPointerException exc) {
            throw new GettingFileListException(CLOUDSTOREERROR);
        }
        return new CloudFileDto.ResponseGetFileList.Answer(lst);
    }

    public void deleteFile(CloudFileDto.RequestDeleteFile.Ask request) throws InputDataException, DeleteFileException { //Удаление файла
        File file = new File(filesPath + "/" + request.getSourceFileName());
        if (!file.exists() || !file.isFile())
            throw new InputDataException(FILENOTFOUND);
        if (!file.delete()) {
            throw new DeleteFileException(FILEDELETEERROR);
        }
    }

    public void uploadFile(CloudFileDto.RequestUploadFile.Ask request) throws InputDataException { //Загрузка файла
        try {
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(filesPath + "/" + request.getTargetFileName()));
            stream.write(request.getBytes());
            stream.close();
        } catch (IOException exc) {
            throw new InputDataException(FILECREATIONERROR);
        }
    }

    public CloudFileDto.ResponseDownloadFile.Answer downloadFile(CloudFileDto.RequestDownloadFile.Ask request) throws InputDataException { //Запрос файла
        FileSystemResource resource;
        resource = new FileSystemResource(filesPath + "/" + request.getSourceFileName());
        if (!resource.exists() | !resource.isFile() | !resource.isReadable()) {
            throw new InputDataException(FILEREADERROR);
        }
        return new CloudFileDto.ResponseDownloadFile.Answer(resource);
    }

    public void renameFile(CloudFileDto.RequestEditFile.Ask request) throws InputDataException, RenameFileException {
        File sourcFile = new File(filesPath + "/" + request.getSourceFileName());
        if (!sourcFile.exists() | sourcFile.isDirectory()) {
            throw new InputDataException(FILENOTFOUND);
        }
        if (!sourcFile.renameTo(new File(filesPath + "/" + request.getTargetFileName()))) {
            throw new RenameFileException(FILERENAMEERROR);
        }
    }
}
