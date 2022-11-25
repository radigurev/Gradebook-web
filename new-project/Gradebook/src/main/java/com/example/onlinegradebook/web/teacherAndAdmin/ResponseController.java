package com.example.onlinegradebook.web.teacherAndAdmin;

import com.example.onlinegradebook.model.binding.AddStudentResponses;
import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ResponseService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tables")
public class ResponseController {

    private final ClassService classesService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ResponseService responseService;

    public ResponseController(ClassService classesService, UserService userService, SubjectService subjectService, ResponseService responseService) {
        this.classesService = classesService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.responseService = responseService;
    }

    @GetMapping("/response")
    public String getClassesResponsePage(Model model) {
        model.addAttribute("classes", classesService.getAllWithId())
                .addAttribute("type","response");
        return "/TeacherAndAdmin/myClassesTable";
    }

    @GetMapping("/responses/{id}")
    public String getResponsePage(@PathVariable String id,Model model) {

        //First table
        model.addAttribute("studentResponses",responseService.getResponses(id));

        //Second table
        model.addAttribute("students",userService.getUsersByClass(id))
                .addAttribute("classes",subjectService.getAll());
        return "/TeacherAndAdmin/responseTable";
    }

    @PostMapping("/responses/add/{id}")
    public String addResponseToStudent(@PathVariable String id, @Valid AddStudentResponses model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("studentResponseBindingModel", model)
                    .addFlashAttribute("org.springframework.validation.BindingResult.studentResponseBindingModel", bindingResult)
                    .addFlashAttribute("badMessage","Length should be at least 3 characters long!");
            return String.format("redirect:/tables/responses/%s",userService.getById(id).getUserClass().getId());
        }

        responseService.saveResponsesForStudent(id,model);

        return String.format("redirect:/tables/responses/%s",id);
    }

    @GetMapping("/remove/response/{id}")
    public String removeResponse(@PathVariable String id) {

        var classId = responseService.getResponseById(id).orElse(null).getStudent().getUserClass().getId();

        responseService.deleteResponse(id);

        return String.format("redirect:/tables/responses/%s",classId);
    }

    @ModelAttribute
    public AddStudentResponses addStudentResponses() {
        return new AddStudentResponses();
    }
}