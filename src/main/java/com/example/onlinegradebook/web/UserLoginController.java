package com.example.onlinegradebook.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @GetMapping("/login")
    public String login(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || authentication instanceof AnonymousAuthenticationToken)
        return "login";

        return "redirect:completeInformation";
    }

    @PostMapping("/login-error")
    public String loginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                         String email, RedirectAttributes attributes){

            attributes.addFlashAttribute("invalid_login",true);
            attributes.addFlashAttribute("email",email);

        return "redirect:login";
    }

}
