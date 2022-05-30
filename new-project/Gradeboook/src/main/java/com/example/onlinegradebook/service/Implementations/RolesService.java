package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.enums.Roles;
import com.example.onlinegradebook.repository.RoleRepository;
import com.example.onlinegradebook.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RolesService implements RoleService {
    private final RoleRepository roleRepository;

    public RolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void RolesInit() {
        if(roleRepository.count()==0) {
            Arrays.stream(Roles.values()).forEach(r -> {
                Role role=new Role();
                role.setRole(r);
                roleRepository.saveAndFlush(role);
            });
        }
    }
}