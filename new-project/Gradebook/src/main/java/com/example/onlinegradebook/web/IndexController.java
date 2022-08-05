package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.ChangeMiddleName;
import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.service.RoleService;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IndexController {

    //TODO restructure whole project break it into different packages

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public IndexController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/")
    public String getIndex() {
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors() || userService.emailIsAlreadyTaken(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",bindingResult)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult)
                    .addFlashAttribute("isThereAnError","Length must be between 3 and 20 characters!");

            return "redirect:/login";
        }

        userService.saveUser(modelMapper.map(userRegisterBindingModel, User.class));
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = authentication.getName();

        model.addAttribute("dashboardInfo", userService.getUserInformationForDashboard(currentPrincipalName))
                .addAttribute("hasMiddleName",userService.hasMiddleName());
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_admin")))
            return "AdminUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_teacher")))
            return "TeacherUI/dashboard";
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_parent")))
            return "ParentUI/dashboard";
        else
            return "StudentUI/dashboard";
    }

    @PostMapping("change/middlename")
    public String changeUsername(ChangeMiddleName middleName) {
        userService.changeMiddleName(middleName);
        return "redirect:/dashboard";
    }

    @PostMapping("/login-error")
    public String loginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                     String email, RedirectAttributes attributes){

        attributes.addFlashAttribute("invalid_login",true);
        attributes.addFlashAttribute("email",email);

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
}
