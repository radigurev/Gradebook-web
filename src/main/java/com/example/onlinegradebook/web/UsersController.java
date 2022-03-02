package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.UserCompleteInformationBindingModel;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.UsersService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ModelAttribute
    public UserCompleteInformationBindingModel completeInformationBindingModel(){
        return new UserCompleteInformationBindingModel();
    }

    @GetMapping("/dashboard")
    public String home() {
        return "dashboard";
    }


    @GetMapping("/completeInformation")
    public String infoComplete() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = usersService.findByEmail(username);

        assert user != null;
        if (user.getPhoneNumber() == null) {
            return "CompleteInfo";
        } else if (!user.isApproved()) {
            return "redirect:notApproved";
        } else
            return "redirect:dashboard";


    }



    @PostMapping("/completeInformation")
    public String postComplete(@Valid UserCompleteInformationBindingModel userCompleteInformationBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userCompleteInformationBindingModel", userCompleteInformationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userCompleteInformationBindingModel", bindingResult);
            return "redirect:completeInformation";
        }


        usersService.saveAdditionalInformation(userCompleteInformationBindingModel);

        return "redirect:completeInformation";
    }

    @GetMapping("/notApproved")
    public String notApproved() {
        return "notApprovedPage";
    }
}

