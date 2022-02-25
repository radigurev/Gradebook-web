package com.example.onlinegradebook.init;

import com.example.onlinegradebook.service.RolesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RolesService rolesService;

    public DBInit(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Override
    public void run(String... args) throws Exception {
        rolesService.initRoles();
    }
}
