package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.admin.subModels.AdminDaysViewModel;
import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;
import com.example.onlinegradebook.model.entity.ClassesSchool;

import java.util.List;
import java.util.Map;

public interface ProgramService {
    void saveProgram(String id, AdminProgramBindingModel model);

   String  getAllPrograms();


}
