package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.GetUserGradesBindingModel;
import com.example.onlinegradebook.model.entity.Grades;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.GradesTableViewModel;

import java.util.List;

public interface GradeService {
    void saveGrades(GetUserGradesBindingModel model,String id);

    List<Grades> getGradesByUser(User user);

    List<GradesTableViewModel> getGradesForUser(User user);

    List<Grades> getAllSchoolGrades();

    Grades getGradeById(String id);

    void removeGrade(String id);
}
