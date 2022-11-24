package com.example.onlinegradebook.web.globalController;

import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    private final UserService userService;

    public GlobalController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("CurrentUser")
    public User currentUser() {
        return userService.getUser();
    }
}
