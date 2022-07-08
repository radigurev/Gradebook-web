package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.admin.AdminNewClassBindingModel;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.view.admin.AdminClassViewModel;
import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.ClassesSchool;

import java.util.List;

public interface ClassService {
    Classes getClass(String number);

    ClassesSchool getClassesSchool(String number);

    List<Classes> getAll();

    void save(AdminNewClassBindingModel newClass);

    List<String> getAllClasses();

    List<ClassesSchool> getClassBySpecialityAndClassAndSchool(String speciality, String classes, School school);

    ClassesSchool getClassesSchoolWithLetter(String number, String letter);

    List<AdminClassViewModel> getAllWithId();

    void addSubjectToClass(String id, String subject);
}
