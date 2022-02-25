package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Roles;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.repository.RoleRepository;
import com.example.onlinegradebook.service.RolesService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleService implements RolesService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
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
}
