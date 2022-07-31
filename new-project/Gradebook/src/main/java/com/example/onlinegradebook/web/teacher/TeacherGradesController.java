package com.example.onlinegradebook.web.teacher;

import com.example.onlinegradebook.model.binding.GetUserGradesBindingModel;
import com.example.onlinegradebook.model.binding.LinkBindingModel;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.GradeService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherGradesController {

    private final ClassService classService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final GradeService gradeService;

    public TeacherGradesController(ClassService classService, UserService userService, SubjectService subjectService, GradeService gradeService) {
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

        return "TeacherUI/myClassesTable";
    }

    @GetMapping("/grade/{id}")
    public String getClassGradesTable(@PathVariable String id, LinkBindingModel linkModel,Model model) {
        model.addAttribute("studentsWithGrades",userService.getStudentsWithGrades(id,linkModel.getSubject()));

        model.addAttribute("studentsWithId",userService.getUsersByClass(classService.getClassesSchoolById(id).getId()))
                .addAttribute("subjects",subjectService.getAll());

        return "/TeacherUI/gradesTable";
    }

    @PostMapping("/grade/{id}")
    public String getClassGrades(@PathVariable String id,GetUserGradesBindingModel model) {

        gradeService.saveGrades(model,id);

        return "redirect:/teacher/grade/"+id;
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
