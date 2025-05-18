package com.example.BaseCMS.exc;

import com.example.BaseCMS.common.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Hidden
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<ObjectError> errors =  ex.getBindingResult().getAllErrors();
        if (!errors.isEmpty()) {
            String message = errors.get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, message, null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Validation failed", null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse<>(500, "Lỗi hệ thống", null));
    }

    @ExceptionHandler(GenericErrorException.class)
    public ResponseEntity<?> genericError(GenericErrorException exception) {
        return new ResponseEntity<>(new ApiResponse<>(exception.getHttpStatus().value(), exception.getMessage(), null), exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException exception) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", exception.getMessage());
        errors.put("statusCode", HttpStatus.UNAUTHORIZED.value());
        errors.put("data",null);
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingHeaders(MissingRequestHeaderException exception) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", "Header '" + exception.getHeaderName() + "' is missing");
        errors.put("statusCode", HttpStatus.BAD_REQUEST.value());
        errors.put("data",null);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<?> usernameOrPasswordInvalidException(WrongCredentialsException exception) {
        return new ResponseEntity<>(new ApiResponse<>(401, "Tài khoản hoặc mật khẩu không đúng", null), HttpStatus.UNAUTHORIZED);
    }
}
