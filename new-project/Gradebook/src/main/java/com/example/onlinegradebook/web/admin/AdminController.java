package com.example.onlinegradebook.web.admin;

import com.example.onlinegradebook.model.binding.MaterialPageViewModel;
import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminGetJsonMaterial;
import com.example.onlinegradebook.model.binding.admin.AdminGetTeacherUpdateBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminNewClassBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminUpdateStudentClass;
import com.example.onlinegradebook.model.binding.json.MaterialImportBindingModel;
import com.example.onlinegradebook.service.*;
import com.example.onlinegradebook.service.Implementations.ClassesService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //TODO start making absence/grades and responses pages

    @ModelAttribute
    public TeacherBindingModel teacherBindingModel() {
        return new TeacherBindingModel();
        }
        @ModelAttribute
        public AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate() {
        return new AdminGetTeacherUpdateBindingModel();
        }

        @ModelAttribute
        public AdminUpdateStudentClass updateStudentClass() {
        return new AdminUpdateStudentClass();
        }

        @ModelAttribute
        public AdminNewClassBindingModel adminNewClassBindingModel() {
        return new AdminNewClassBindingModel();
        }

        @ModelAttribute
        public AdminGetJsonMaterial adminGetJsonMaterial() {
        return new AdminGetJsonMaterial();
        }

    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassService classesService;
    private final Gson gson;
    private final MaterialService materialService;
    private final SpecialityService specialityService;
    public AdminController(UserService userService, SubjectService subjectService, ClassesService classesService, Gson gson, MaterialService materialService, SpecialityService specialityService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.classesService = classesService;
        this.gson = gson;
        this.materialService = materialService;
        this.specialityService = specialityService;
    }


    @GetMapping("/teachers")
    public String addTeachers(Model model) {
        model.addAttribute("teachers",userService.getAllTeacherNames())
                .addAttribute("subjects", subjectService.getAll())
                .addAttribute("classes", classesService.getAll());
       return "/AdminUI/teachersTable";
    }

    @PostMapping("/teachers")
    public String addTeacher(TeacherBindingModel teacherBindingModel) {

        userService.saveTeacher(teacherBindingModel);

        return "redirect:/admin/teachers";
    }

    //@{/admin/teacher/add/subject/{id}/(id=${t.id})}

    @PostMapping("/teacher/add/subject/{id}")
    public String addSubjectToTeacher(@PathVariable String id, AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate) {

        userService.updateTeacherSubject(adminGetTeacherUpdate.getUpdate(),id);

        return "redirect:/admin/teachers";
    }

    @PostMapping("/teacher/add/class/{id}")
    public String addClassToTeacher(@PathVariable String id, AdminGetTeacherUpdateBindingModel adminGetTeacherUpdate) {

        userService.updateTeacherClass(id,adminGetTeacherUpdate.getUpdate());

        return "redirect:/admin/teachers";
    }

    @GetMapping("/teacher/remove/{id}")
    public String removeTeacher(@PathVariable String id) {

        //TODO UI BUG fix buttons (add wrappers)

        userService.removeTeacher(id);

        return "redirect:/admin/teachers";
    }

    @GetMapping("/material")
    public String getMaterialPage(Model model) {
        model.addAttribute("nonTaken",materialService.getMaterialsByIfItsTaken(false));
        model.addAttribute("Taken",materialService.getMaterialsByIfItsTaken(true));
        model.addAttribute("classes",classesService.getAllClasses());
        model.addAttribute("subjects",subjectService.getAll());

        return "AdminUI/materialTable";
    }


    @PostMapping("/material")
    public String getMaterials(AdminGetJsonMaterial test) {

        MaterialImportBindingModel[] materials=gson.fromJson(test.getTest(),MaterialImportBindingModel[].class);
        System.out.println();
        materialService.saveMaterials(materials);
        return "redirect:material";
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
    @GetMapping("/classes")
    public String getClassesPage(Model model) {
        //returns students without classes
        model.addAttribute("nonAssignedStudents",userService.getUsersBySchoolAndClass());
        //getting existing classes
        model.addAttribute("classes",classesService.getAll());
        //get classes with head teacher
        model.addAttribute("schoolClasses",userService.getClassWithTeacher());
        model.addAttribute("speciality",specialityService.getAll());
        //Returns users with school and class
        model.addAttribute("studentsAndTheirClass",userService.getUsersBySchoolInJson(userService.getUser().getSchool()));
        return "/AdminUI/classTable";
    }

    @PostMapping("/classes/save/class")
    public String saveNewClass(AdminNewClassBindingModel newClass) {
        classesService.save(newClass);

        return "redirect:/admin/classes";
    }

    @GetMapping("/students")
    public String getStudentsPage(Model model) {
        model.addAttribute("unassignedUsers",userService.getUsersBySchool("None"));
        model.addAttribute("schoolStudents",userService.getUsersBySchool(userService.getUser().getSchool().getName()));
        return "/AdminUI/studentsTable";
    }

    @GetMapping("/students/add/{id}")
    public String addUserToSchool(@PathVariable String id) {

        userService.updateUserSchool(id);

        return "redirect:/admin/students";
    }

    @GetMapping("students/remove/{id}")
    public String removeUserFromSchool(@PathVariable String id) {
        userService.removeUserFromSchool(id);

        return "redirect:/admin/students";
    }

    @GetMapping("students/remove/class/{id}")
    public String removeUserFromClass(@PathVariable String id) {
        System.out.println();
        userService.removeUserFromClass(id);
        return "redirect:/admin/classes";
    }

    @PostMapping("/students/add/class/{id}")
    public String addStudentToClass(@PathVariable String id,AdminUpdateStudentClass adminUpdateStudentClass) {
        userService.addUserToClass(id,adminUpdateStudentClass.getUserClass());

        return "redirect:/admin/classes";
    }

}
