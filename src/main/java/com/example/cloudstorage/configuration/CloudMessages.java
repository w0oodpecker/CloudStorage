package com.example.cloudstorage.configuration;

import org.springframework.stereotype.Component;

@Component
public class CloudMessages {
    public static final String AUTHORIZATION = "Auth-Token";
    public static final String BADLOGIN = "Пользователь не найден";
    public static final String USERUNOUTHORIZED = "Пользователь не авторизован";
    public static final String FOLDERNOTFOUND = "Каталог не существует";
    public static final String CLOUDSTOREERROR = "Ошибка файлового репозитория";
    public static final String FILENOTFOUND = "Файл не существует";
    public static final String FILEDELETEERROR = "Ошибка удаления файла";
    public static final String FILECREATIONERROR = "Ошибка создания или загрузки файла";
    public static final String FILEREADERROR = "Файл не существует или не может быть прочитан";
    public static final String FILERENAMEERROR = "Не удалось выполнить переименование файла";
}
