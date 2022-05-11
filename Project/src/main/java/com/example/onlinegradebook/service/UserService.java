package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.UserCompleteInformationBindingModel;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.model.view.TeacherEnterGradesTableView;

import java.util.List;

public interface UserService {
    void saveUser(UserRegistrationService map);

    User findByEmail(String username);

    void saveAdditionalInformation(UserCompleteInformationBindingModel userCompleteInformationBindingModel);

    List<TeacherEnterGradesTableView> findAll();
}
