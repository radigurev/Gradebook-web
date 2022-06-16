package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.service.Implementations.ClassesService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @ModelAttribute
    public TeacherBindingModel teacherBindingModel() {
        return new TeacherBindingModel();
        }

    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassesService classesService;

    public AdminController(UserService userService, SubjectService subjectService, ClassesService classesService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.classesService = classesService;
    }


    @GetMapping("/teachers")
    public String addTeachers(Model model) {
        model.addAttribute("teachers",userService.getAllTeacherNames())
                .addAttribute("subjects", subjectService.getAll())
                .addAttribute("classes", classesService.getAll());
       return "/AdminUI/teachersTable";
    }

    @PostMapping("/teachers")
    public String addTeacher(TeacherBindingModel teacherBindingModel) {

        userService.saveTeacher(teacherBindingModel);
        return "redirect:/admin/teachers";
    }

}
