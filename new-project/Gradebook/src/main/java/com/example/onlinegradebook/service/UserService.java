package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.DashboardInfoText;

public interface UserService {
    void saveUser(User user);

    DashboardInfoText getUserInformationForDashboard(String name);

}
