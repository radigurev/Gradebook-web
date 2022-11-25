package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.AddStudentResponses;
import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.model.entity.ResponseStudents;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsResponsesViewModel;
import com.example.onlinegradebook.model.view.ResponseViewModel;

import java.util.List;
import java.util.Optional;

public interface ResponseService {
    void init();

    void saveResponsesForStudent(String id, AddStudentResponses model);

    List<StudentsResponsesViewModel> getResponses(String id);

    List<ResponseViewModel> getResponsesForStudent();

    List<ResponseStudents> getAllResponses();

    List<ResponseStudents> getUserResponses();

    Optional<ResponseStudents> getResponseById(String id);

    void deleteResponse(String id);
}
