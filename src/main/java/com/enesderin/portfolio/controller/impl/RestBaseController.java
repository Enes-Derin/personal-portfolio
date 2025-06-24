package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.model.RootEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestBaseController {

    public <T> RootEntity<T> ok (T data) {
        return RootEntity.ok(data);
    }

    public <T> RootEntity<T> error (String errorMessage) {
        return RootEntity.error(errorMessage);
    }
}
