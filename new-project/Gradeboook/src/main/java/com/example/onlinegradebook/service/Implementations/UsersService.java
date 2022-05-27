package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService implements UserService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void saveUser(User user) {
        Set<Role> roles=new HashSet<>();
        user.setRole(roles);

        userRepository.saveAndFlush(user);
    }
}
