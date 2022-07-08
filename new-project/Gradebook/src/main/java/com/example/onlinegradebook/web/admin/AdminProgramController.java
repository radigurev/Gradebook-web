package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;
import com.example.onlinegradebook.model.view.admin.AdminTeacherProgramTableViewModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ProgramService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProgramController {

    private final UserService userService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final ProgramService programService;
    public AdminProgramController(UserService userService, ClassService classService, SubjectService subjectService, ProgramService programService) {
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
        //TODO populate program table + make it so admin can see every class program

        List<AdminTeacherProgramTableViewModel> allTeacherNamesAndSubjects = userService.getAllTeacherNamesAndSubjects();


        model.addAttribute("teachers",userService.getAllTeacherNamesAndSubjects());

        model.addAttribute("classes",classService.getAll());

        model.addAttribute("subjects",subjectService.getAll());

        return "/AdminUI/programTable";
    }

    @PostMapping("/program/add/{id}")
    public String getProgramForTeacher(@PathVariable String id,AdminProgramBindingModel model) {

        programService.saveProgram(id,model);

        return "redirect:/admin/program";
    }

}
