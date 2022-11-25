package com.example.onlinegradebook.web.teacherAndAdmin;

import com.example.onlinegradebook.model.binding.GetUserGradesBindingModel;
import com.example.onlinegradebook.model.binding.LinkBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.GradeService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tables")
public class GradesController {

    private final ClassService classService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final GradeService gradeService;

    public GradesController(ClassService classService, UserService userService, SubjectService subjectService, GradeService gradeService) {
        this.classService = classService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    public String getGradesClassesPage(Model model) {

        model.addAttribute("classes", classService.getAllWithId())
                .addAttribute("type","grade")
                .addAttribute("subjects",subjectService.getAll());

        return "/TeacherAndAdmin/myClassesTable";
    }

    @GetMapping("/grade/{id}")
    public String getClassGradesTable(@PathVariable String id, LinkBindingModel linkModel,Model model) {



        model.addAttribute("studentsWithGrades",userService.getStudentsWithGrades(id,linkModel.getSubject()));

        model.addAttribute("studentsWithId",userService.getUsersByClass(classService.getClassesSchoolById(id).getId()))
                .addAttribute("subjects",subjectService.getAll());

        return "/TeacherAndAdmin/gradesTable";
    }

    @PostMapping("/grade/{id}")
    public String getClassGrades(@PathVariable String id, @Valid GetUserGradesBindingModel model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userGradesBindingModel", model)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userGradesBindingModel", bindingResult)
                    .addFlashAttribute("badMessage","Length must be at least 3 characters long!");

        }

        gradeService.saveGrades(model,id);

        return "redirect:/tables/grade/"+id+"?subject="+model.getSubject();
    }

    @GetMapping("/grade/remove/{id}")
    public String removeGrade(@PathVariable String id) {

        var classId = gradeService.getGradeById(id).getStudent().getUserClass().getId();

        gradeService.removeGrade(id);

        return "redirect:/tables/grade/"+classId;
    }

    @ModelAttribute
    public GetUserGradesBindingModel userGradesBindingModel() {
        return new GetUserGradesBindingModel();
    }

    @ModelAttribute
    public LinkBindingModel linkBindingModel() {
        return new LinkBindingModel();
    }
}
