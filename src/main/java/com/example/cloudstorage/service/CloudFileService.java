package com.example.cloudstorage.service;

import com.example.cloudstorage.exeptions.InputDataException;
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

    private String filesPath;

    CloudFileService(@Value("${files.path}") String filesPath) {
        this.filesPath = filesPath;
    }


    public ArrayList<?> getFileList() { //Получение списка файлов репозитория
        File dir = new File(filesPath);

        if(!dir.exists()) throw new InputDataException("Каталог не существует");

        List<CloudFile> lst = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isFile())
                lst.add(new CloudFile(file.getName(), file.length()));
        }
        return (ArrayList<CloudFile>) lst;
    }


    public void deleteFile(String fileName) { //Удаление файла
        File file = new File(filesPath + "/" + fileName);
        if (!file.delete()) {
            throw new InputDataException("Ошибка удаления файла");
        }
    }


    public void uploadFile(String fileName, MultipartFile file) { //Загрузка файла
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filesPath + "/" + fileName)));
            stream.write(bytes);
            stream.close();
        } catch (IOException exc) {
            throw new InputDataException("Ошибка создания или загрузки файла");
        }
    }

    public FileSystemResource downloadFile(String fileName) { //Запрос файла
        FileSystemResource resource;
        resource = new FileSystemResource(filesPath +"/" + fileName);
        return resource;
    }
}
