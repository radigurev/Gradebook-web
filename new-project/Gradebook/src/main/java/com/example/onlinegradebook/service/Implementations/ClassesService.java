package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.repository.ClassesRepository;
import com.example.onlinegradebook.repository.ClassesSchoolRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassesService implements ClassService {

    private final ClassesRepository classesRepository;
    private final ClassesSchoolRepository classesSchoolRepository;
    private final UserService userService;

    public ClassesService(ClassesRepository classesRepository, ClassesSchoolRepository classesSchoolRepository,@Lazy UserService userService) {
        this.classesRepository = classesRepository;
        this.classesSchoolRepository = classesSchoolRepository;
        this.userService = userService;
    }

    @Override
    public Classes getClass(String number) {
        return classesRepository.findByClassNumber(number);
    }

    @Override
    public List<Classes> getAll() {

        String school = userService.getUser().getSchool().getName();

            List<Classes> classes=new ArrayList<>();
            classesSchoolRepository.findAll().stream()
                    .filter(c -> !c.getClasses().getClassNumber().equals("None"))
                    .forEach(c -> {
                        if(c.getSchool().getName().equals(school)) {
                            classes.add(c.getClasses());
                        }
                    });
            return classes;
    }
}
