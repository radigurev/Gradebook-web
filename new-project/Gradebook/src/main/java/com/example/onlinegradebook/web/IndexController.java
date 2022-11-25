package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.ChangeMiddleName;
import com.example.onlinegradebook.model.binding.ChangePasswordModel;
import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.service.RoleService;
import com.example.onlinegradebook.service.UserService;
import com.example.onlinegradebook.service.UsersSubjectsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class IndexController {

    //TODO restructure whole project break it into different packages

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final UsersSubjectsService usersSubjectsService;
    private final PasswordEncoder passwordEncoder;

    public IndexController(UserService userService, ModelMapper modelMapper, RoleService roleService, UsersSubjectsService usersSubjectsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.usersSubjectsService = usersSubjectsService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/")
    public String getIndex() {
        if (userService.getUser() != null)
            return "redirect:/dashboard";
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || userService.emailIsAlreadyTaken(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", bindingResult)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult)
                    .addFlashAttribute("isThereAnError", "Length must be between 3 and 20 characters!");

            return "login";
        }

        userService.saveUser(modelMapper.map(userRegisterBindingModel, User.class));
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = authentication.getName();
        var info = userService.getUserInformationForDashboard(currentPrincipalName);
        model.addAttribute("dashboardInfo", info)
                .addAttribute("hasMiddleName", userService.hasMiddleName());
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_admin")))
            return "AdminUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_teacher")))
            return "TeacherUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_parent")))
            return "ParentUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_student")))
            return "StudentUI/dashboard";
        else {
            model.addAttribute("dashboardInfoAdmin", userService.getUserInformationForDashboardAdmin());
            return "SuperAdminUI/dashboard";
        }
    }

    @GetMapping("/UserProfile")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return "redirect:/";

        model.addAttribute("user", userService.getUser());
        model.addAttribute("subjects", usersSubjectsService.getUserSubjects(userService.getUser()));
        return "UserProfile";
    }

    @PostMapping("/UserProfile/update/{id}")
    public String updateUser(@PathVariable String id, @Valid User user,@RequestParam("uploadfile") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
        List<String> errors = new ArrayList<>();
        if (user.getFirstName() != null && user.getFirstName().trim().equals("")) {
            //add RedirectAttribute
            errors.add("Полето 'Име' е задължително!");
        }

        if (user.getLastName() != null && user.getLastName().trim().equals("")) {
            errors.add("Полето 'Фамилия' е задължително!");
        }

        if (errors.isEmpty())
            userService.updateUser(user);
        else
            redirectAttributes.addFlashAttribute("Errors", errors);

        if(!multipartFile.isEmpty())
            userService.saveImage(multipartFile);
        return "redirect:/UserProfile";
    }

    @PostMapping("/UserProfile/changePassword")
    public String changePass(@Valid ChangePasswordModel changePasswordModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        List<String> errors = new ArrayList<>();
        boolean isOld = false;
        if (changePasswordModel.getOldPassword() != null)
            isOld = passwordEncoder.matches(changePasswordModel.getOldPassword(), userService.getUser().getPassword());

        if (changePasswordModel.getOldPassword() == null || changePasswordModel.getOldPassword().length() < 3) {
            errors.add("Полето 'Стара Парола' е задължително да има поне 3 символа!");
        } else if (!isOld) {
            errors.add("Грешна стара парола!");
        }
        if (changePasswordModel.getNewPassword() == null || changePasswordModel.getNewPassword().length() < 3) {
            errors.add("Полето 'Нова Парола' е задължително да има поне 3 символа!");
        }
        if (!Objects.equals(changePasswordModel.getNewPassword(), changePasswordModel.getConfirmPassword())) {
            errors.add("Паролите трябва да съвпадат!");
        }

        if (errors.isEmpty())
            userService.updateUserPassword(changePasswordModel);
        else
            redirectAttributes.addFlashAttribute("passErrors", errors);

        return "redirect:/UserProfile";
    }

    @PostMapping("/login-error")
    public String loginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                             String email, RedirectAttributes attributes) {

        attributes.addFlashAttribute("invalid_login", true);
        attributes.addFlashAttribute("email", email);

        return "redirect:/";
    }



    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }

    @ModelAttribute
    public ChangeMiddleName changeMiddleName() {
        return new ChangeMiddleName();
    }

    @ModelAttribute
    public ChangePasswordModel changePasswordModel() {
        return new ChangePasswordModel();
    }

}
