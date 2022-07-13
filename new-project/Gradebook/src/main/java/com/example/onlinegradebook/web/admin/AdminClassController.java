package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.admin.AdminNewClassBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SpecialityService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminClassController {

    private final UserService userService;
    private final ClassService classService;
    private final SpecialityService specialityService;

    public AdminClassController(UserService userService, ClassService classService, SpecialityService specialityService) {
        this.userService = userService;
        this.classService = classService;
        this.specialityService = specialityService;
    }


    @GetMapping("/classes")
    public String getClassesPage(Model model) {
        //returns students without classes
        model.addAttribute("nonAssignedStudents",userService.getUsersBySchoolAndClass());
        //getting existing classes
        model.addAttribute("classes",classService.getAll());
        //get classes with head teacher
        model.addAttribute("schoolClasses",userService.getClassWithTeacher());
        model.addAttribute("speciality",specialityService.getAll());
        //Returns users with school and class
        model.addAttribute("studentsAndTheirClass",userService.getUsersBySchoolInJson(userService.getUser().getSchool()));
        return "/AdminUI/classTable";
    }

    @PostMapping("/classes/save/class")
    public String saveNewClass(AdminNewClassBindingModel newClass) {
        classService.save(newClass);

        return "redirect:/admin/classes";
    }

    @ModelAttribute
    public AdminNewClassBindingModel adminNewClassBindingModel() {
        return new AdminNewClassBindingModel();
    }
}
