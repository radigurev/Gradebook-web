package com.example.onlinegradebook.web.teacher;

import com.example.onlinegradebook.model.binding.AddTestBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teacher")
@Controller
public class TeacherTestController {

    private final SubjectService subjectService;
    private final ClassService classService;

    private final TestService testService;

    public TeacherTestController(SubjectService subjectService, ClassService classService, TestService testService) {
        this.subjectService = subjectService;
        this.classService = classService;
        this.testService = testService;
    }

    @GetMapping("/tests")
    public String getTestTable(Model model) {
        model.addAttribute("subjects",subjectService.getAll())
                    .addAttribute("classes",classService.getAll())
                        .addAttribute("tests",testService.getAll());

        return "/AdminUI/testsTable";
    }

    @PostMapping("/test/add/{id}")
    public String getNewTest(@PathVariable String id,AddTestBindingModel model) {

        testService.saveTest(model,id);

        return "redirect:/admin/tests";
    }

    @ModelAttribute
    public AddTestBindingModel addTestBindingModel(){
        return new AddTestBindingModel();
    }
}
