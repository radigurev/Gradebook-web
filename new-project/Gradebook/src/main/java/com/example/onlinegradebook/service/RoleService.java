package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Role;

import java.util.HashSet;
import java.util.List;

public interface RoleService {
    void RolesInit();

    Role getStudentRole();

    Role getTeacherRole();

    List<Role> returnRoles();
}
