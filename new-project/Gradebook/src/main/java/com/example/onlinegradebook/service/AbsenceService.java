package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.AddAbsencesBindingModel;
import com.example.onlinegradebook.model.entity.AbsenceStudent;
import com.example.onlinegradebook.model.view.AbsenceViewModel;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsAbsenceViewModel;

import java.util.List;

public interface AbsenceService {

    void init();

    List<StudentsAbsenceViewModel> getUsersWithAbsence(String id);

    void removeAbsence(String id);


    AbsenceStudent findById(String id);

    AbsenceViewModel getAbsencesForUser();

    List<AbsenceStudent> getAllAbsences();

    List<AbsenceStudent> getUserAbsences();

    void saveAbsences(String id, AddAbsencesBindingModel addAbsencesBindingModel);

    void changeToLate(String id);
}
