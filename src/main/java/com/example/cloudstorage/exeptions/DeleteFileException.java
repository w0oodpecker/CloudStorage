package com.example.cloudstorage.exeptions;

public class DeleteFileException extends RuntimeException {
    public DeleteFileException(String msg) {
        super(msg);
    }
}
