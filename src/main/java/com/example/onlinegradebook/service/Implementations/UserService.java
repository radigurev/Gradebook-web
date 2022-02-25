package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.repository.RoleRepository;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserService implements UsersService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(RoleRepository roleRepository, ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserRegistrationService map) {
        LocalDate localDate=LocalDate.now();
        User user=modelMapper.map(map, User.class);
        user.setCreatedAt(java.sql.Date.valueOf(localDate));
        user.setRole(roleRepository.findByAccountType(AccountType.STUDENT));
        user.setPassword(passwordEncoder.encode(map.getPassword()));
        userRepository.saveAndFlush(user);
    }
}
