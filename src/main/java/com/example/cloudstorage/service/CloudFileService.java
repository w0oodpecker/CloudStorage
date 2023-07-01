package com.example.cloudstorage.service;

import com.example.cloudstorage.exceptions.*;
import com.example.cloudstorage.model.CloudFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudFileService {

    private final String filesPath;

    CloudFileService(@Value("${files.path}") String filesPath) {
        this.filesPath = filesPath;
    }

    public ArrayList<?> getFileList() throws InputDataException, GettingFileListException, UnauthorizedException { //Получение списка файлов репозитория
        File dir = new File(filesPath);
        if (!dir.exists()) throw new InputDataException("Каталог не существует");
        List<CloudFile> lst = new ArrayList<>();
        try {
            for (File file : dir.listFiles()) {
                if (file.isFile())
                    lst.add(new CloudFile(file.getName(), file.length()));
            }
        } catch (NullPointerException exc) {
            throw new GettingFileListException("Ошибка репозитория");
        }
        return (ArrayList<CloudFile>) lst;
        // TODO: 6/28/2023 Добавить проверку валидности акаунта
    }

    public void deleteFile(String fileName) throws InputDataException, UnauthorizedException, DeleteFileException { //Удаление файла
        File file = new File(filesPath + "/" + fileName);
        if (!file.exists() || !file.isFile())
            throw new InputDataException("Файл не существует");
        if (!file.delete()) {
            throw new DeleteFileException("Ошибка удаления файла");
        }
        // TODO: 6/28/2023 Добавить проверку валидности акаунта

    }

    public void uploadFile(String fileName, MultipartFile file) throws InputDataException, UnauthorizedException { //Загрузка файла
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filesPath + "/" + fileName)));
            stream.write(bytes);
            stream.close();
        } catch (IOException exc) {
            throw new InputDataException("Ошибка создания или загрузки файла");
        }
        // TODO: 6/28/2023 Добавить проверку валидности аккаунта
    }

    public FileSystemResource downloadFile(String fileName) throws InputDataException, UnauthorizedException { //Запрос файла
        FileSystemResource resource;
        resource = new FileSystemResource(filesPath + "/" + fileName);
        if (!resource.exists() | !resource.isFile() | !resource.isReadable()) {
            throw new InputDataException("Файл не существуе или не может быть прочитан");
        }
        return resource;
        // TODO: 6/28/2023 Добавить проверку валидности акаунта 
    }

    public void renameFile(String fileName, CloudFile newFile) throws InputDataException, UnauthorizedException, RenameFileException {
        File sourcFile = new File(filesPath + "/" + fileName);
        if (!sourcFile.exists() | sourcFile.isDirectory()) {
            throw new InputDataException("Файл не существует");
        }
        if (!sourcFile.renameTo(new File(filesPath + "/" + newFile.getFilename()))) {
            throw new RenameFileException("Не удалось выполнить переименование файла");
        }
        // TODO: 6/29/2023 Добавить проверку валидности акаунта
    }
}
