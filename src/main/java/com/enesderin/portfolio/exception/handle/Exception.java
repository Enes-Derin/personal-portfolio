package com.enesderin.portfolio.exception.handle;

import lombok.Data;

import java.util.Date;

@Data
public class Exception<E> {

    private String path;
    private Date createTime;
    private E message;

}
