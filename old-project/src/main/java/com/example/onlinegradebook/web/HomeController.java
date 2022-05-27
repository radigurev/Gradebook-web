package com.example.onlinegradebook.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
        @GetMapping("/")
    public String homePage(){

            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            return authentication instanceof AnonymousAuthenticationToken ? "index" : "redirect:/users/completeInformation";

        }
}
