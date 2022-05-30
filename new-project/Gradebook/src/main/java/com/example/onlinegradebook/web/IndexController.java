package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class IndexController {

        private final UserService userService;
        private final ModelMapper modelMapper;
        public IndexController(UserService userService, ModelMapper modelMapper) {
                this.userService = userService;
                this.modelMapper = modelMapper;
        }

        @ModelAttribute
        public UserRegisterBindingModel userRegisterBindingModel() {
                return new UserRegisterBindingModel();
        }

        @GetMapping("/")
        public String getIndex() {
            return "login";
        }

        @PostMapping("/register")
        public String register(UserRegisterBindingModel userRegisterBindingModel) {
                userService.saveUser(modelMapper.map(userRegisterBindingModel, User.class));
                return "login";
        }
}
