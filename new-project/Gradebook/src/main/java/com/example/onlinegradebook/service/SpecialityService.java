package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Speciality;
import com.example.onlinegradebook.model.view.SuperAdmin.SpecialitiesViewModel;

import java.util.List;

public interface SpecialityService {
    List<Speciality> getAll();

    Speciality getSpeciality(String speciality);

    int getCount();

    List<SpecialitiesViewModel> getSpecialities();

    void removeSpeciality(String id);

    void saveSpeciality(SpecialitiesViewModel specialitiesViewModel);
}
