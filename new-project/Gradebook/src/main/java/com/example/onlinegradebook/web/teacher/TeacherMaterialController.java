package com.example.onlinegradebook.web.teacher;

import com.example.onlinegradebook.model.binding.admin.AdminGetJsonMaterial;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.MaterialService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherMaterialController {

    private final MaterialService materialService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final UserService userService;

    public TeacherMaterialController(MaterialService materialService, ClassService classService, SubjectService subjectService, UserService userService) {
        this.materialService = materialService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @GetMapping("/material")
    public String getMaterialPage(Model model) {
        model.addAttribute("nonTaken",materialService.getMaterialsByIfItsTaken(false));
        model.addAttribute("Taken",materialService.getMaterialsByIfItsTaken(true));
        model.addAttribute("classes",classService.getAllClasses());
        model.addAttribute("subjects",subjectService.getAll());

        return "TeacherUI/materialTable";
    }


    @PostMapping("/material")
    public String getMaterials(AdminGetJsonMaterial test) {
        materialService.saveMaterials(test);
        return "redirect:/teacher/material";
    }

    @GetMapping("/material/undo/{id}")
    public String undoMaterial(@PathVariable String id) {
        materialService.takeMaterial(id,false,null);
        return "redirect:/teacher/material";
    }
    @GetMapping("/material/take/{id}")
    public String takeMaterial(@PathVariable String id) {
        materialService.takeMaterial(id,true,userService.getUser());
        return "redirect:/teacher/material";
    }

}
