package com.example.onlinegradebook.web.student;

import com.example.onlinegradebook.service.AbsenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping("/view")
@Controller
public class AbsenceViewController {

    private final AbsenceService absenceService;

    public AbsenceViewController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping("/absence")
    public String getAbsenceTable(Model model) {

        model.addAttribute("absences",absenceService.getAbsencesForUser());

        return "StudentUI/absenceTable";
    }
}
