package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.admin.AdminGetJsonMaterial;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.MaterialService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminMaterialController {

    private final MaterialService materialService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final UserService userService;

    public AdminMaterialController(MaterialService materialService, ClassService classService, SubjectService subjectService, UserService userService) {
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

        return "AdminUI/materialTable";
    }


    @PostMapping("/material")
    public String getMaterials(AdminGetJsonMaterial test) {
        System.out.println();
        materialService.saveMaterials(test);
        return "redirect:/admin/material";
    }

    @GetMapping("/material/undo/{id}")
    public String undoMaterial(@PathVariable String id) {
        materialService.takeMaterial(id,false,null);
        return "redirect:/admin/material";
    }
    @GetMapping("/material/take/{id}")
    public String takeMaterial(@PathVariable String id) {
        materialService.takeMaterial(id,true,userService.getUser());
        return "redirect:/admin/material";
    }
    @GetMapping("/material/remove/{id}")
    public String removeMaterial(@PathVariable String id) {
        materialService.removeMaterial(id);
        return "redirect:/admin/material";
    }

    @ModelAttribute
    public AdminGetJsonMaterial adminGetJsonMaterial() {
        return new AdminGetJsonMaterial();
    }
}
