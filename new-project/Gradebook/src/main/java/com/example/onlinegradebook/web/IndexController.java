package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.service.RoleService;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public IndexController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
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

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

          if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_admin")))
            return "AdminUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_teacher")))
        return "TeacherUI/dashboard";
         else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_parent")))
            return "ParentUI/dashboard";
         else
            return "StudentUI/dashboard";
    }
}
