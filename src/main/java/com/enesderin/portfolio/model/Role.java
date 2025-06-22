package com.enesderin.portfolio.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN;


    @Override
    public String getAuthority() {
        return "ROLE_"+ name();
    }
}
