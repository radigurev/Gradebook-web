package Gradebook.service.impl;

import Gradebook.model.entity.Role;
import Gradebook.model.entity.Users;
import Gradebook.repository.RoleRepository;
import Gradebook.repository.UserRepository;
import Gradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void userRegister(Users user) {
        Set<Role> roles=new HashSet<>();
        roles.add(roleRepository.getByRole("student"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roles);
    }
}
