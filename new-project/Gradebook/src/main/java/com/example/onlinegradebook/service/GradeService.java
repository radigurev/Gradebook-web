package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.GetUserGradesBindingModel;
import com.example.onlinegradebook.model.entity.Grades;
import com.example.onlinegradebook.model.entity.User;

import java.util.List;

public interface GradeService {
    void saveGrades(GetUserGradesBindingModel model,String id);

    List<Grades> getGradesByUser(User user);
}
