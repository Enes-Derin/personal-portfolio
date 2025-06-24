package com.enesderin.portfolio.exception.handle;

import com.enesderin.portfolio.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ApiError> globalExceptionHandler(BaseException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }


    public <E> ApiError<E> createApiError(E message , WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        Exception exception = new Exception();
        exception.setMessage(message);
        exception.setCreateTime(new Date());
        exception.setPath(request.getDescription(false).substring(4));
        apiError.setException(exception);
        return apiError;
    }
}
