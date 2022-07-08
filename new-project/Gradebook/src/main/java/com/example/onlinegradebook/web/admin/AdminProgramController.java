package com.example.onlinegradebook.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.Table;

@Controller
@RequestMapping("/admin")
public class AdminProgramController {

    @GetMapping("/program")
    public String getProgramPage() {
        return "/AdminUI/programTable";
    }

}
