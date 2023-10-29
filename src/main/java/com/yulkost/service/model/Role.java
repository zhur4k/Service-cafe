package com.yulkost.service.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER, ADMIN, ACCOUNTANT;

    @Override
    public String getAuthority() {
        return name();
    }
}