package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.view.SuperAdmin.AdminAndSchoolViewModel;

import java.util.List;

public interface SchoolService {
    School findSchool(String school);

    int getSchoolCount();

    void saveSchool(School school);

    void deleteSchoolById(String id);
}
