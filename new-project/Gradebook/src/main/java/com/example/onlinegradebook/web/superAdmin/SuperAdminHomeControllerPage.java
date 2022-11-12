package com.example.onlinegradebook.web.superAdmin;

import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.binding.superAdmin.AdminAndSchoolBindingModel;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.SuperAdmin.SpecialitiesViewModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SchoolService;
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

    private final SchoolService schoolService;

    private final SpecialityService specialityService;
    public SuperAdminHomeControllerPage(UserService userService, ClassService classService, SchoolService schoolService, SpecialityService specialityService) {
        this.userService = userService;
        this.classService = classService;
        this.schoolService = schoolService;
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
                    .addFlashAttribute("isThereAnError","Email already taken!");

            return "/SuperAdminUI/AdminAndSchoolAdd";
        }

        userService.saveUserAndSchool(adminAndSchoolBindingModel);

        return "/SuperAdminUI/AdminAndSchoolAdd";
    }

    @GetMapping("/remove/school/{id}")
    public String removeSchoolWithAdmins(@PathVariable String id) {

        userService.deleteAdminsAndSchool(id);

        return "redirect:/super/schools";
    }

    @GetMapping("/admin")
    public String getAdminTable(Model model) {

        model.addAttribute("Admins",userService.getAllAdmins());

        return "/SuperAdminUI/adminTable";
    }

    @GetMapping("/remove/admin/{id}")
    public String deleteAdmin(@PathVariable String id) {

        userService.deleteUser(id);

        return "redirect:/super/admin";
    }

    @GetMapping("/add/admin/school/{id}")
    public String addNewAdminToSchool(@PathVariable String id,Model model) {

       model.addAttribute("SchoolName", schoolService.findSchoolById(id));

        return "SuperAdminUI/AddAdminToSchool";
    }

    @PostMapping("/add/admin/school/{id}")
    public String newAdminToSchool(@PathVariable String id, Model model, UserRegisterBindingModel userRegisterBindingModel) {

        userService.saveAdminToSchool(id,userRegisterBindingModel);

        model.addAttribute("SchoolName", schoolService.findSchoolById(id));

        return "SuperAdminUI/AddAdminToSchool";
    }

    @GetMapping("/users")
    public String getUserTable(Model model) {

        model.addAttribute("Users",userService.getAllUsers());

        return "/SuperAdminUI/usersTable";
    }

    @GetMapping("/specialities")
    public String getSpecialityTable(Model model) {

        model.addAttribute("specialities",specialityService.getSpecialities());

        return "/SuperAdminUI/specialityTable";
    }

    @PostMapping("/specialities")
    public  String saveSpeciality(SpecialitiesViewModel specialitiesViewModel) {

        specialityService.saveSpeciality(specialitiesViewModel);

        return "redirect:/super/specialities";
    }

    @GetMapping("/remove/speciality/{id}")
    public String removeSpeciality(@PathVariable String id) {

        specialityService.removeSpeciality(id);

        return "redirect:/super/specialities";
    }


    @ModelAttribute
    public AdminAndSchoolBindingModel adminAndSchoolBindingModel()
    {
        return  new AdminAndSchoolBindingModel();
    }
    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
    @ModelAttribute
    public SpecialitiesViewModel specialitiesViewModel() {
        return  new SpecialitiesViewModel();
    }
}
