package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.RoleService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public void saveUser(User user) {
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getStudentRole());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public DashboardInfoText getUserInformationForDashboard(String name) {
        User user = userRepository.findByEmail(name).orElse(null);

        return new DashboardInfoText(String.format("%s %s",user.getFirstName(),user.getLastName()),user.getSchool().getName(),user.getEmail(),user.getUserClass().getClassNumber());
    }


}
