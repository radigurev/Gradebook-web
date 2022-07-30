package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;

public interface ProgramService {
    void saveProgram(String id, AdminProgramBindingModel model);

   String  getAllPrograms();


}
