package com.example.onlinegradebook.web.teacher;

import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ProgramService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherProgramController {

    private final UserService userService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final ProgramService programService;
    public TeacherProgramController(UserService userService, ClassService classService, SubjectService subjectService, ProgramService programService) {
        this.userService = userService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.programService = programService;
    }

    @ModelAttribute
    AdminProgramBindingModel adminProgramBindingModel() {
        return new AdminProgramBindingModel();
    }

    @GetMapping("/program")
    public String getProgramPage(Model model) {

        model.addAttribute("programs",programService.getAllPrograms());

//        model.addAttribute("teachers",userService.getAllTeacherNamesAndSubjects());

//        model.addAttribute("classes",classService.getAll());

//        model.addAttribute("subjects",subjectService.getAll());

        return "/TeacherUI/programTable";
    }

}
