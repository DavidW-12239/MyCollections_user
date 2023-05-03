package com.myCollections.user.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotFoundException extends RuntimeException {
    private HttpStatus code;

    public NotFoundException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
