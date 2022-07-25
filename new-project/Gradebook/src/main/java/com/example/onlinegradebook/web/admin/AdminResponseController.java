package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ResponseService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminResponseController {

    private final ClassService classesService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ResponseService responseService;

    public AdminResponseController(ClassService classesService, UserService userService, SubjectService subjectService, ResponseService responseService) {
        this.classesService = classesService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.responseService = responseService;
    }

    @GetMapping("/response")
    public String getClassesResponsePage(Model model) {
        model.addAttribute("classes", classesService.getAllWithId());

        return "/AdminUI/myClassesTable";
    }

    @GetMapping("/responses/{id}")
    public String getResponsePage(@PathVariable String id,Model model) {

        //First table
        model.addAttribute("studentResponses",responseService.getResponses(id));

        //Second table
        model.addAttribute("students",userService.getUsersByClass(id))
                .addAttribute("classes",subjectService.getAll());
        return "/AdminUI/responseTable";
    }

    @PostMapping("/responses/add/{id}")
    public String addResponseToStudent(@PathVariable String id,StudentResponseBindingModel model) {

        responseService.saveResponseForStudent(id,model);

        return String.format("redirect:/admin/responses/%s",userService.getById(id).getUserClass().getId());
    }

    @ModelAttribute
    public StudentResponseBindingModel studentResponseBindingModel() {
        return new StudentResponseBindingModel();
    }
}
