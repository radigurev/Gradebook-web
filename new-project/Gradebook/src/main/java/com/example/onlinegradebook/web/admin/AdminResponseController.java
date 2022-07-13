package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminResponseController {

    private final ClassService classesService;
    private final UserService userService;

    public AdminResponseController(ClassService classesService, UserService userService) {
        this.classesService = classesService;
        this.userService = userService;
    }


    @GetMapping("/response")
    public String getClassesResponsePage(Model model) {

        model.addAttribute("classes", classesService.getAllWithId());

        return "/AdminUI/myClassesTable";
    }

    @GetMapping("/responses/{id}")
    public String getResponsePage(@PathVariable String id,Model model) {

        model.addAttribute("students",userService.getUsersByClass(id));

        return "/AdminUI/responseTable";
    }
}
