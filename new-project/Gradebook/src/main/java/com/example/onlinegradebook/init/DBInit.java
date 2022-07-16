package com.example.onlinegradebook.init;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.repository.ClassesRepository;
import com.example.onlinegradebook.repository.ClassesSchoolRepository;
import com.example.onlinegradebook.repository.ResponseRepository;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.ResponseService;
import com.example.onlinegradebook.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final SchoolRepository schoolRepository;
    private final ClassesRepository classesRepository;
    private final ClassesSchoolRepository classesSchoolRepository;
    private final ResponseRepository responseRepository;
    private final ResponseService responseService;
    public DBInit(RoleService roleService, SchoolRepository schoolRepository, ClassesRepository classesRepository, ClassesSchoolRepository classesSchoolRepository, ResponseRepository responseRepository, ResponseService responseService) {
        this.roleService = roleService;
        this.schoolRepository = schoolRepository;
        this.classesRepository = classesRepository;
        this.classesSchoolRepository = classesSchoolRepository;
        this.responseRepository = responseRepository;
        this.responseService = responseService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.RolesInit();
        if (schoolRepository.count()==0) {
            School school=new School();
            school.setName("None");
            schoolRepository.saveAndFlush(school);

        }
        if(classesRepository.count()==0) {
            Classes classes=new Classes();
            classes.setClassNumber("None");
            classesRepository.saveAndFlush(classes);
            ClassesSchool classesSchool=new ClassesSchool();
            classesSchool.setClasses(classes);
            classesSchoolRepository.saveAndFlush(classesSchool);
        }

        if (responseRepository.count()==0) {
            responseService.init();
        }
    }
}
