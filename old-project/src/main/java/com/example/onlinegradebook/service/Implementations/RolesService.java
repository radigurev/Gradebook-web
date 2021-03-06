package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Roles;
import com.example.onlinegradebook.model.entity.enums.AccountType;
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
    public void initRoles() {
        if(roleRepository.count()==0){
            Arrays.stream(AccountType.values()).forEach(role -> {
                Roles roles = new Roles();
                roles.setAccountType(role);
                roleRepository.saveAndFlush(roles);
            });
        }
    }

    @Override
    public Roles findByAccountType(AccountType student) {
        return roleRepository.findByAccountType(student);
    }
}
