package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.admin.AdminSubjectBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminSubjectsController {

    private final ClassService classService;
    private final SubjectService subjectService;

    public AdminSubjectsController(ClassService classService, SubjectService subjectService) {
        this.classService = classService;
        this.subjectService = subjectService;
    }

    @ModelAttribute
    public AdminSubjectBindingModel adminSubjectBindingModel() {
        return new AdminSubjectBindingModel();
    }

    @GetMapping("/subjects")
    public String getSubjectsPage(Model model) {

        model.addAttribute("classesWithId",classService.getAllWithId());
        model.addAttribute("subjects",subjectService.getAll());

        return "/AdminUI/subjectsTable";
    }

    @PostMapping("/subjects")
    public String getNewSubject(@Valid AdminSubjectBindingModel adminSubjectBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes)  {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminSubjectBindingModel", adminSubjectBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.adminSubjectBindingModel", bindingResult)
                    .addFlashAttribute("badCredentials","Length must be at least 3 characters");
            return "redirect:/admin/subjects";
        }

        subjectService.saveSubject(adminSubjectBindingModel.getSubject());
        return "redirect:/admin/subjects";
    }

    @GetMapping("/subject/class/add/{id}")
    public String addSubjectToClass(@PathVariable String id, AdminSubjectBindingModel subject) {

        classService.addSubjectToClass(id,subject.getSubject());

        return "redirect:/admin/subjects";
    }

}
