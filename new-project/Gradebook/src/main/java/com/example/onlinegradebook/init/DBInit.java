package com.example.onlinegradebook.init;

import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.repository.*;
import com.example.onlinegradebook.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService roleService;
    private final SchoolRepository schoolRepository;
    private final ClassesRepository classesRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassesSchoolRepository classesSchoolRepository;
    private final ResponseRepository responseRepository;
    private final SchoolService schoolservice;
    private final ClassService classService;
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final AbsenceRepository absenceRepository;
    private final AbsenceService absenceService;
    public DBInit(RoleService roleService, SchoolRepository schoolRepository, ClassesRepository classesRepository, PasswordEncoder passwordEncoder, ClassesSchoolRepository classesSchoolRepository, ResponseRepository responseRepository, SchoolService schoolservice, ClassService classService, UserRepository userRepository, ResponseService responseService, AbsenceRepository absenceRepository, AbsenceService absenceService) {
        this.roleService = roleService;
        this.schoolRepository = schoolRepository;
        this.classesRepository = classesRepository;
        this.passwordEncoder = passwordEncoder;
        this.classesSchoolRepository = classesSchoolRepository;
        this.responseRepository = responseRepository;
        this.schoolservice = schoolservice;
        this.classService = classService;
        this.userRepository = userRepository;
        this.responseService = responseService;
        this.absenceRepository = absenceRepository;
        this.absenceService = absenceService;
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

        if (absenceRepository.count()==0) {
            absenceService.init();
        }

       if(userRepository.count() == 0) {
            User user = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getSuperAdminRole());
            user.setRole(roles);
            user.setPassword(passwordEncoder.encode("password"));
            user.setSchool(schoolservice.findSchool("None"));
            user.setFirstName("Admin");
            user.setMiddleName("");
            user.setLastName("Admin");
            user.setEmail("radi.gurev@gmail.com");
            user.setUserClass(classService.getClassesSchool("None"));
            userRepository.saveAndFlush(user);
       }
    }
}
