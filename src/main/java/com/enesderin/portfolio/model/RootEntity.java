package com.enesderin.portfolio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootEntity<T> {
    private boolean result;
    private String errorMessage;
    private T data;

    public static  <T> RootEntity ok (T data) {
        RootEntity<T> result = new RootEntity<T>();
        result.setResult(true);
        result.setErrorMessage(null);
        result.setData(data);
        return result;
    }

    public static <T> RootEntity error (String errorMessage) {
        RootEntity<T> result = new RootEntity<T>();
        result.setResult(false);
        result.setErrorMessage(errorMessage);
        result.setData(null);
        return result;
    }
}
