package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.User;

public interface UserService {
    void saveUser(User user);

    String getName(String name);
}
