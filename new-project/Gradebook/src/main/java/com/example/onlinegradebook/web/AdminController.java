package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.view.admin.AdminGetTeacherUpdate;
import com.example.onlinegradebook.service.Implementations.ClassesService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin")
public class AdminController {

    @ModelAttribute
    public TeacherBindingModel teacherBindingModel() {
        return new TeacherBindingModel();
        }
        @ModelAttribute
        public AdminGetTeacherUpdate adminGetTeacherUpdate() {
        return new AdminGetTeacherUpdate();
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

    //@{/admin/teacher/add/subject/{id}/(id=${t.id})}

    @PostMapping("/teacher/add/subject/{id}")
    public String addSubjectToTeacher(@PathVariable String id,AdminGetTeacherUpdate adminGetTeacherUpdate) {

        userService.updateTeacherSubject(adminGetTeacherUpdate.getUpdate(),id);

        return "redirect:/admin/teachers";
    }

    @PostMapping("/teacher/add/class/{id}")
    public String addClassToTeacher(@PathVariable String id, AdminGetTeacherUpdate adminGetTeacherUpdate) {
        userService.updateTeacherClass(id,adminGetTeacherUpdate.getUpdate());

        return "redirect:/admin/teachers";
    }

    @GetMapping("/teacher/remove/{id}")
    public String removeTeacher(@PathVariable String id) {

        userService.removeTeacher(id);

        return "redirect:/admin/teachers";
    }

    @GetMapping("/material")
    public String getMaterialPage() {
        return "AdminUI/materialTable";
    }
    @GetMapping("/classes")
    public String getClassesPage() {
        return "/AdminUI/classTable";
    }


}
