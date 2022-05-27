package Gradebook.service.impl;

import Gradebook.model.entity.Role;
import Gradebook.model.entity.enums.Roles;
import Gradebook.repository.RoleRepository;
import Gradebook.service.RoleService;
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
        if(roleRepository.count()==0) {
            Arrays.stream(Roles.values()).forEach(r -> {
                Role role=new Role();
                role.setRole(r);
                roleRepository.saveAndFlush(role);
            });


        }
    }
}
