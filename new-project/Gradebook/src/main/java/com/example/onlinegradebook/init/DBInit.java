package com.example.onlinegradebook.init;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.repository.ClassesRepository;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final SchoolRepository schoolRepository;
    private final ClassesRepository classesRepository;
    public DBInit(RoleService roleService, SchoolRepository schoolRepository, ClassesRepository classesRepository) {
        this.roleService = roleService;
        this.schoolRepository = schoolRepository;
        this.classesRepository = classesRepository;
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
        }
    }
}
