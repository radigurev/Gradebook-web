package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Roles;
import com.example.onlinegradebook.model.entity.enums.AccountType;

public interface RoleService {

    void initRoles();

    Roles findByAccountType(AccountType student);
}
