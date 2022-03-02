package com.example.onlinegradebook.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    @GetMapping("/entergrades")
    public String enterGradeTable(){
        return "EnterGrades";
    }
}
