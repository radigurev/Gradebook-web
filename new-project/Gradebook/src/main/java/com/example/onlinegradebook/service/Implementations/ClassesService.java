package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.admin.AdminNewClassBindingModel;
import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.repository.ClassesRepository;
import com.example.onlinegradebook.repository.ClassesSchoolRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SpecialityService;
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

    private final SpecialityService specialityService;

    public ClassesService(ClassesRepository classesRepository, ClassesSchoolRepository classesSchoolRepository, @Lazy UserService userService, SpecialityService specialityService) {
        this.classesRepository = classesRepository;
        this.classesSchoolRepository = classesSchoolRepository;
        this.userService = userService;
        this.specialityService = specialityService;
    }

    @Override
    public Classes getClass(String number) {
        return classesRepository.findByClassNumber(number).orElse(null);
    }

    //getting all classes with letter
    @Override
    public List<Classes> getAll() {

        String school = userService.getUser().getSchool().getName();

            List<Classes> classes=new ArrayList<>();
            classesSchoolRepository.findAll().stream()
                    .filter(c -> !c.getClasses().getClassNumber().equals("None"))
                    .forEach(c -> {
                        if(c.getSchool().getName().equals(school)) {
                            Classes Class=new Classes();
                            Class.setClassNumber(String.format("%s%s",c.getClasses().getClassNumber(),c.getLetter()));
                            classes.add(Class);
                        }
                    });
            return classes;
    }

    //Saving class
    @Override
    public void save(AdminNewClassBindingModel newClass) {
        Character[] letters=new Character[] {'а','б','в','г','д','е','ж','з','и','к','л','м','н','о'};

        School school = userService.getUser().getSchool();

        Classes number = classesRepository.findByClassNumber(newClass.getNumber()).orElse(null);
        if(number==null) {
            Classes classes=new Classes();
            classes.setClassNumber(newClass.getNumber());
            classesRepository.saveAndFlush(classes);
            number= classesRepository.findByClassNumber(newClass.getNumber()).orElse(null);
        }
        List<ClassesSchool> byClassNumber = classesSchoolRepository.findAllByClassesAndSchool(number,school);
        ClassesSchool newSchoolClass=new ClassesSchool();
        newSchoolClass.setSchool(school);
        newSchoolClass.setClasses(number);
        newSchoolClass.setLetter(String.valueOf(letters[byClassNumber.size()]));
        newSchoolClass.setSpeciality(specialityService.getSpeciality(newClass.getSpeciality()));

        classesSchoolRepository.saveAndFlush(newSchoolClass);
    }

    //Using it only for classes with speciality
    @Override
    public List<String> getAllClasses() {

        List<ClassesSchool> allBySchool = classesSchoolRepository.findAllBySchool(userService.getUser().getSchool());
        List<String> classes=new ArrayList<>();
        allBySchool.forEach(s -> {
            String Class=String.format("%s %s",s.getClasses().getClassNumber(),s.getSpeciality().getName());
            if(!classes.contains(Class))
            classes.add(Class);
        });
        return classes;
    }

    @Override
    public List<ClassesSchool> getClassBySpecialityAndClass(String speciality, String classes) {
        return classesSchoolRepository.findByClassesAndSubject(speciality,classes);
    }
}
