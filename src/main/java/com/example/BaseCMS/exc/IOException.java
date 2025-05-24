package com.example.BaseCMS.exc;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Builder
public class IOException extends RuntimeException {
    public IOException(String message) {
        super(message);
    }
}
