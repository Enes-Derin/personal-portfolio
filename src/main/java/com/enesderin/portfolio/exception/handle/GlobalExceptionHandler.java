package com.enesderin.portfolio.exception.handle;

import com.enesderin.portfolio.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ApiError> globalExceptionHandler(BaseException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        for (ObjectError objectError : exception.getBindingResult().getFieldErrors()) {
            String fieldName = ((FieldError)objectError).getField();
            if (errors.containsKey(fieldName)) {
                errors.put(fieldName, addMapValue(errors.get(fieldName),objectError.getDefaultMessage()));
            }else{
                errors.put(fieldName,addMapValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }
        return ResponseEntity.badRequest().body(createApiError(errors,request));
    }

    private List<String> addMapValue(List<String> list, String newValue) {
        list.add(newValue);
        return list;
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
