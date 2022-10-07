package com.example.onlinegradebook.web.superAdmin;

import com.example.onlinegradebook.model.binding.admin.AdminNewClassBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminUpdateStudentClass;
import com.example.onlinegradebook.model.binding.superAdmin.AdminAndSchoolBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SpecialityService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/super")
public class SuperAdminHomeControllerPage {

    private  final UserService userService;
    private final ClassService classService;

    private final SpecialityService specialityService;
    public SuperAdminHomeControllerPage(UserService userService, ClassService classService, SpecialityService specialityService) {
        this.userService = userService;
        this.classService = classService;
        this.specialityService = specialityService;
    }

    @GetMapping("/schools")
    public String getSchools(Model model)
    {
        model.addAttribute("Schools",userService.getSchoolWithTeachers());

        return "/SuperAdminUI/schoolTable";
    }

    @GetMapping("/add/schoolAndAdmin")
    public String addSchoolWithAdmin()
    {
        return "/SuperAdminUI/AdminAndSchoolAdd";
    }

    @PostMapping("/add/schoolAndAdmin")
    public String addNewSchoolAndAdmin(@Valid AdminAndSchoolBindingModel adminAndSchoolBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors() || userService.emailIsAlreadyTaken(adminAndSchoolBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",bindingResult)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult)
                    .addFlashAttribute("isThereAnError","Length must be between 3 and 20 characters!");

            return "/SuperAdminUI/AdminAndSchoolAdd";
        }

        userService.saveUserAndSchool(adminAndSchoolBindingModel);

        return "/SuperAdminUI/AdminAndSchoolAdd";
    }

    @GetMapping("/remove/school/{id}")
    public String removeSchoolWithAdmins(@PathVariable String id)
    {
        userService.deleteAdminsAndSchool(id);

        return "redirect:/super/schools";
    }
    @ModelAttribute
    public AdminAndSchoolBindingModel adminAndSchoolBindingModel()
    {
        return  new AdminAndSchoolBindingModel();
    }
}
