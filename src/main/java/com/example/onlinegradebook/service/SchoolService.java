package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.School;

public interface SchoolService {
    School findBySchool(String school);
}
