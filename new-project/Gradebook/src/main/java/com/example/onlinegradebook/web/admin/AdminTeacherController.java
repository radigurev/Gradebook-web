package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminGetTeacherUpdateBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import com.example.onlinegradebook.service.UsersSubjectsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminTeacherController {

    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassService classService;
    private final UsersSubjectsService usersSubjectsService;

    public AdminTeacherController(UserService userService, SubjectService subjectService, ClassService classService, UsersSubjectsService usersSubjectsService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.classService = classService;
        this.usersSubjectsService = usersSubjectsService;
    }

    @GetMapping("/teachers")
    public String addTeachers(Model model) {
        model.addAttribute("teachers",userService.getAllTeacherNames())
                .addAttribute("subjects", subjectService.getAll())
                .addAttribute("classes", classService.getAll());
        return "/AdminUI/teachersTable";
    }

    @PostMapping("/teachers")
    public String addTeacher(TeacherBindingModel teacherBindingModel) {

        userService.saveTeacher(teacherBindingModel);

        return "redirect:/admin/teachers";
    }

    //@{/admin/teacher/add/subject/{id}/(id=${t.id})}

    @PostMapping("/teacher/add/subject/{id}")
    public String addSubjectToTeacher(@PathVariable String id, AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate) {

        usersSubjectsService.addSubjectToUser(adminGetTeacherUpdate.getUpdate(),id);

        return "redirect:/admin/teachers";
    }

    @PostMapping("/teacher/add/class/{id}")
    public String addClassToTeacher(@PathVariable String id, AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate) {

        userService.addClassToUser(id,adminGetTeacherUpdate.getUpdate());

        return "redirect:/admin/teachers";
    }

    @GetMapping("/teacher/remove/{id}")
    public String removeTeacher(@PathVariable String id) {

        //TODO UI BUG fix buttons (add wrappers)

        userService.removeTeacher(id);

        return "redirect:/admin/teachers";
    }

    @ModelAttribute
    public AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate() {
        return new AdminGetTeacherUpdateBindingModel();
    }

    @ModelAttribute
    public TeacherBindingModel teacherBindingModel() {
        return new TeacherBindingModel();
    }

}
