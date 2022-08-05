package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsResponsesViewModel;
import com.example.onlinegradebook.model.view.ResponseViewModel;

import java.util.List;

public interface ResponseService {
    void init();

    void saveResponseForStudent(String id, StudentResponseBindingModel model);

    List<StudentsResponsesViewModel> getResponses(String id);

    List<ResponseViewModel> getResponsesForStudent();
}
