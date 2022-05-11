package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.GradesBindingModel;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final UserService usersService;

    public TeacherController(UserService usersService) {
        this.usersService = usersService;
    }

    @ModelAttribute
    public GradesBindingModel gradesBindingModel() {
        return new GradesBindingModel();
    }

    @GetMapping("/entergrades")
    public String enterGradeTable(Model model) {
        model.addAttribute("students",usersService.findAll());
        return "EnterGrades";
    }

    @PostMapping("/entergrades")
    public String getGrades(GradesBindingModel gradesBindingModel) {
        System.out.println(gradesBindingModel.getGrades());
        return "EnterGrades";
    }
}
