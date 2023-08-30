package com.hyunki.pointapi.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalError.class)
    protected ResponseEntity<?> handleInternalException(InternalError e) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(-1)
                .message(e.getMessage())
                .errorCode("INTERNAL_SERVER_ERROR")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(NotFound.class)
    protected ResponseEntity<?> handleNotFoundException(NotFound e) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(-1)
                .message(e.getMessage())
                .errorCode("NOT_FOUND_ERROR")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(-1)
                .message(e.getMessage())
                .errorCode("ILLEGAL_STATE_EXCEPTION_ERROR")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(NotEnoughPointException.class)
    protected ResponseEntity<?> handleNotEnoughPointException(NotEnoughPointException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(-1)
                .message(e.getMessage())
                .errorCode("NOT_ENOUGH_POINT")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
