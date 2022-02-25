package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.web.UserRegisterController;

public interface UsersService {
    void saveUser(UserRegistrationService map);
}
