package com.example.onlinegradebook.model.view.SuperAdmin;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;

public class AdminAndSchoolViewModel {

    private User user;

    private School school;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
