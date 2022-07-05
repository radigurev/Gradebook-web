package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Speciality;

import java.util.List;

public interface SpecialityService {
    List<Speciality> getAll();

    Speciality getSpeciality(String speciality);
}
