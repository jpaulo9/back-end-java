package com.rest.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileStorageException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MyFileStorageException(String ex) {
        super(ex);
    }

    public MyFileStorageException(String ex, Throwable twbl) {
        super(ex, twbl);
    }

}
