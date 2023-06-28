package com.example.cloudstorage.exeptions;

import javax.security.auth.message.AuthException;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
