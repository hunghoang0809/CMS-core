package com.example.BaseCMS.exc;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class GenericErrorException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public GenericErrorException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
