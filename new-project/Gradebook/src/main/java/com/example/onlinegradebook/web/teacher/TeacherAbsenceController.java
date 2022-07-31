package com.example.onlinegradebook.web.teacher;

import com.example.onlinegradebook.service.AbsenceService;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherAbsenceController {

    private final ClassService classService;
    private final UserService userService;
    private final AbsenceService absenceService;


    public TeacherAbsenceController(ClassService classService, UserService userService, AbsenceService absenceService) {
        this.classService = classService;
        this.userService = userService;
        this.absenceService = absenceService;
    }

    @GetMapping("/absences")
    public String getClassesAbsencePage(Model model) {

        model.addAttribute("classes", classService.getAllWithId())
                .addAttribute("type","absence");

        return "/TeacherUI/myClassesTable";
    }

    @GetMapping("/absence/{id}")
    public String getAbsenceTable(@PathVariable String id, Model model) {

        model.addAttribute("studentsWithAbsences",absenceService.getUsersWithAbsence(id));

        model.addAttribute("studentsWithId",userService.getUsersByClass(classService.getClassesSchoolById(id).getId()));

        return "/TeacherUI/absenceTable";
    }

    @GetMapping("/add/absence/{id}")
    public String addAbsenceToStudent(@PathVariable String id) {

        absenceService.saveUserAbsence(id);

        return String.format("redirect:/teacher/absence/%s",userService.getById(id).getUserClass().getId());
    }

    @GetMapping("/add/late/{id}")
    public String addLateToStudent(@PathVariable String id) {

        absenceService.saveUserLate(id);

        return String.format("redirect:/teacher/absence/%s",userService.getById(id).getUserClass().getId());
    }

    @GetMapping("/remove/absence/{id}")
    public String removeLateFromStudent(@PathVariable String id) {

        String schoolId = absenceService.findById(id).getStudent().getUserClass().getId();

        absenceService.removeAbsence(id);

        return String.format("redirect:/teacher/absence/%s",schoolId);
    }
}
