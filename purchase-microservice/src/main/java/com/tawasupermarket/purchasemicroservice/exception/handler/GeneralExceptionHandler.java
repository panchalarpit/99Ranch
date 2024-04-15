package com.tawasupermarket.purchasemicroservice.exception.handler;

import com.tawasupermarket.purchasemicroservice.exception.ExceptionMessage;
import com.tawasupermarket.purchasemicroservice.exception.ResourceNotFoundException;
import com.tawasupermarket.purchasemicroservice.exception.UserAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionMessage> resourceNotFound(ResourceNotFoundException exception, WebRequest webRequest) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .error(exception.getMessage())
                .timestamp(new Date())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionMessage> accessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(String.valueOf(HttpStatus.UNAUTHORIZED))
                .error(exception.getMessage())
                .timestamp(new Date())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ExceptionMessage> userAlreadyExist(UserAlreadyExist exception, WebRequest webRequest) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(String.valueOf(HttpStatus.NOT_ACCEPTABLE))
                .error(exception.getMessage())
                .timestamp(new Date())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(String.valueOf(HttpStatus.NOT_ACCEPTABLE))
                .error(exception.getFieldError().toString())
                .timestamp(new Date())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionMessage,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> generalException(Exception exception, WebRequest webRequest) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .error(exception.getMessage())
                .timestamp(new Date())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
