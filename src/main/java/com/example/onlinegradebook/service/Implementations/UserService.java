package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.repository.RoleRepository;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserService implements UsersService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public UserService(RoleRepository roleRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserRegistrationService map) {
        LocalDate localDate=LocalDate.now();
        User user=modelMapper.map(map, User.class);
        user.setCreatedAt(java.sql.Date.valueOf(localDate));
        user.setRole(roleRepository.findByAccountType(AccountType.STUDENT));
        userRepository.saveAndFlush(user);
    }
}
