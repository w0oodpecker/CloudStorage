package com.example.cloudstorage.exeptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
