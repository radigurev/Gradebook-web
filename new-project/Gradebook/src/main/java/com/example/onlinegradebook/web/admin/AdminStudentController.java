package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.admin.AdminUpdateStudentClass;
import com.example.onlinegradebook.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {

    //TODO start making absence,grades responses and exam pages

    private final UserService userService;
    public AdminStudentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/students")
    public String getStudentsPage(Model model) {
        model.addAttribute("unassignedUsers",userService.getUsersBySchool("None"));
        model.addAttribute("schoolStudents",userService.getUsersBySchool(userService.getUser().getSchool().getName()));
        return "/AdminUI/studentsTable";
    }

    @GetMapping("/students/add/{id}")
    public String addUserToSchool(@PathVariable String id) {

        userService.updateUserSchool(id);

        return "redirect:/admin/students";
    }

    @GetMapping("students/remove/{id}")
    public String removeUserFromSchool(@PathVariable String id) {
        userService.removeUserFromSchool(id);

        return "redirect:/admin/students";
    }

    @GetMapping("students/remove/class/{id}")
    public String removeUserFromClass(@PathVariable String id) {
        System.out.println();
        userService.removeUserFromClass(id);
        return "redirect:/admin/classes";
    }

    @PostMapping("/students/add/class/{id}")
    public String addStudentToClass(@PathVariable String id,AdminUpdateStudentClass adminUpdateStudentClass) {
        userService.addClassToUser(id, adminUpdateStudentClass.getUserClass());

        return "redirect:/admin/classes";
    }

}
