package com.example.onlinegradebook.web.teacherAndAdmin;

import com.example.onlinegradebook.model.binding.AddAbsencesBindingModel;
import com.example.onlinegradebook.service.AbsenceService;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.Implementations.SubjectsService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tables")
public class AbsenceController {

    private final ClassService classService;
    private final UserService userService;
    private final AbsenceService absenceService;
    private final SubjectsService subjectsService;


    public AbsenceController(ClassService classService, UserService userService, AbsenceService absenceService, SubjectsService subjectsService) {
        this.classService = classService;
        this.userService = userService;
        this.absenceService = absenceService;
        this.subjectsService = subjectsService;
    }

    @GetMapping("/absences")
    public String getClassesAbsencePage(Model model) {

        model.addAttribute("classes", classService.getAllWithId())
                .addAttribute("type","absence");

        return "/TeacherAndAdmin/myClassesTable";
    }

    @GetMapping("/absence/{id}")
    public String getAbsenceTable(@PathVariable String id, Model model) {

        model.addAttribute("studentsWithAbsences",absenceService.getUsersWithAbsence(id));

        model.addAttribute("studentsWithId",userService.getUsersByClass(classService.getClassesSchoolById(id).getId()));

        model.addAttribute("subjects",subjectsService.getAll());

        return "/TeacherAndAdmin/absenceTable";
    }

    @PostMapping("/absence/{id}")
    public String getAbsences(@PathVariable String id, AddAbsencesBindingModel addAbsencesBindingModel) {

        absenceService.saveAbsences(id,addAbsencesBindingModel);
        return "redirect:/tables/absence/"+id;
    }

    @GetMapping("/remove/absence/{id}")
    public String removeLateFromStudent(@PathVariable String id) {

        String schoolId = absenceService.findById(id).getStudent().getUserClass().getId();

        absenceService.removeAbsence(id);

        return String.format("redirect:/tables/absence/%s",schoolId);
    }

    @GetMapping("/remove/to/late/{id}")
    public String changeToLate(@PathVariable String id) {
        String schoolId = absenceService.findById(id).getStudent().getUserClass().getId();

        absenceService.changeToLate(id);

        return String.format("redirect:/tables/absence/%s",schoolId);
    }

    @ModelAttribute
    public AddAbsencesBindingModel addAbsencesBindingModel() {
        return new AddAbsencesBindingModel();
    }
}
