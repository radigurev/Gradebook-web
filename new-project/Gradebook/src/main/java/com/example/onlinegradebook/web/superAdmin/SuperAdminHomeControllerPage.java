package com.example.onlinegradebook.web.superAdmin;

import com.example.onlinegradebook.model.binding.superAdmin.AdminAndSchoolBindingModel;
import com.example.onlinegradebook.model.view.SuperAdmin.AdminAndSchoolViewModel;
import com.example.onlinegradebook.service.SchoolService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/super")
public class SuperAdminHomeControllerPage {

    private final SchoolService schoolService;
    private  final UserService userService;
    public SuperAdminHomeControllerPage(SchoolService schoolService, UserService userService) {
        this.schoolService = schoolService;
        this.userService = userService;
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

    @ModelAttribute
    public AdminAndSchoolBindingModel adminAndSchoolBindingModel()
    {
        return  new AdminAndSchoolBindingModel();
    }
}
