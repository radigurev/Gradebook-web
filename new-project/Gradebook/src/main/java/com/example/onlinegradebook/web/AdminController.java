package com.example.onlinegradebook.web;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.binding.Test;
import com.example.onlinegradebook.model.binding.admin.AdminGetTeacherUpdateBindingModel;
import com.example.onlinegradebook.model.binding.admin.AdminUpdateStudentClass;
import com.example.onlinegradebook.service.Implementations.ClassesService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin")
public class AdminController {

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
        public Test test() {
        return new Test();
        }

    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassesService classesService;

    public AdminController(UserService userService, SubjectService subjectService, ClassesService classesService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.classesService = classesService;
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

        //TODO Populate tables and selects
        //TODO parse new material for BD via JSON string from js to java
        model.addAttribute("classes",classesService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        //TODO make functionality for adding, removing material

        return "AdminUI/materialTable";
    }

    @PostMapping("/material")
    public String getMaterials(Test test) {

        System.out.println(test.getTest());

        return "redirect:material";
    }
    @GetMapping("/classes")
    public String getClassesPage(Model model) {
        //returns students without classes
        model.addAttribute("nonAssignedStudents",userService.getUsersBySchoolAndClass());
        //getting existing classes
        model.addAttribute("classes",classesService.getAll());
        //get classes with head teacher
        model.addAttribute("schoolClasses",userService.getClassWithTeacher());
        //Returns users with school and class
        model.addAttribute("studentsAndTheirClass",userService.getUsersBySchoolInJson(userService.getUser().getSchool()));
        return "/AdminUI/classTable";
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
