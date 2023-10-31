package com.yulkost.service.dto;


import com.yulkost.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserEditDto {
    private final List<User> users;

    public UserEditDto(){
        this.users = new ArrayList<>();
    }
    public UserEditDto(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

}
