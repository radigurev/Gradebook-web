package com.example.onlinegradebook.init;

import com.example.onlinegradebook.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;

    public DBInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.RolesInit();
    }
}
