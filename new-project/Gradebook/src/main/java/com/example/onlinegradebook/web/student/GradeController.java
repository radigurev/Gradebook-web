package com.example.onlinegradebook.web.student;

import com.example.onlinegradebook.service.GradeService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class GradeController {

    private final GradeService gradeService;

    private final UserService userService;

    public GradeController(GradeService gradeService, UserService userService) {
        this.gradeService = gradeService;
        this.userService = userService;
    }

    @GetMapping("/grades")
    public String getGradesTable(Model model) {

        gradeService.getGradesForUser(userService.getUser());

        model.addAttribute("grades",gradeService.getGradesForUser(userService.getUser()));

        return "/StudentUI/gradesTable";
    }
}
