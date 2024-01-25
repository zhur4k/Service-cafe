package com.yulkost.service.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    CASHIER, ADMIN, ACCOUNTANT;

    @Override
    public String getAuthority() {
        return name();
    }
}