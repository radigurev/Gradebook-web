package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.AbsenceStudent;
import com.example.onlinegradebook.model.view.AbsenceViewModel;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsAbsenceViewModel;

import java.util.List;

public interface AbsenceService {
    void saveUserAbsence(String id);

    void init();

    void saveUserLate(String id);

    List<StudentsAbsenceViewModel> getUsersWithAbsence(String id);

    void removeAbsence(String id);


    AbsenceStudent findById(String id);

    AbsenceViewModel getAbsencesForUser();
}
