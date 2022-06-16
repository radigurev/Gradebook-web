package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.AdminTeacherTableViewModel;
import com.example.onlinegradebook.model.view.DashboardInfoText;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    DashboardInfoText getUserInformationForDashboard(String name);

    void saveTeacher(TeacherBindingModel teacherBindingModel);

    List<AdminTeacherTableViewModel> getAllTeacherNames();
}
