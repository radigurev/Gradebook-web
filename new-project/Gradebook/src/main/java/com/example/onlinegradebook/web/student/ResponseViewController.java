package com.example.onlinegradebook.web.student;

import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ResponseService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ResponseViewController {


    private final ResponseService responseService;

    public ResponseViewController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("/response")
    public String getClassesResponsePage(Model model) {
        responseService.getResponsesForStudent();

        model.addAttribute("responses",responseService.getResponsesForStudent());

        return "/StudentUI/responseTable";
    }

    @ModelAttribute
    public StudentResponseBindingModel studentResponseBindingModel() {
        return new StudentResponseBindingModel();
    }
}
