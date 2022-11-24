package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.SubjectSchool;
import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.UsersSubjects;
import com.example.onlinegradebook.repository.ClassesSubjectsRepository;
import com.example.onlinegradebook.repository.SubjectRepository;
import com.example.onlinegradebook.repository.SubjectSchoolRepository;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectsService implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final SubjectSchoolRepository subjectSchoolRepository;

    private final ClassesSubjectsRepository classesSubjectsRepository;

    public SubjectsService(SubjectRepository subjectRepository, @Lazy UserService userService, SubjectSchoolRepository subjectSchoolRepository, ClassesSubjectsRepository classesSubjectsRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.subjectSchoolRepository = subjectSchoolRepository;
        this.classesSubjectsRepository = classesSubjectsRepository;
    }

    @Override
    public List<Subjects> getAll() {

//        return subjectSchoolRepository.getAllBySchool(userService.getUser().getSchool()).stream().map(SubjectSchool::getSubject).collect(Collectors.toList());
        return subjectRepository.findAll();
    }



    @Override
    public Subjects getSubjectByName(String name) {
        return subjectRepository.getByName(name).orElse(null);
    }

    @Override
    public void saveSubject(String subjectName) {
        Subjects subject = getSubjectByName(subjectName);
        if (subject == null) {
            subject = new Subjects();
            subject.setName(subjectName);
            subjectRepository.saveAndFlush(subject);
        }
        School adminSchool = userService.getUser().getSchool();

        SubjectSchool subjectSchool = subjectSchoolRepository.findBySchoolAndSubject(adminSchool, subject).orElse(null);
        if (subjectSchool == null) {
            subjectSchool = new SubjectSchool();
            subjectSchool.setSubject(subject);
            subjectSchool.setSchool(adminSchool);
            subjectSchoolRepository.saveAndFlush(subjectSchool);
        }
    }

    @Override
    public SubjectSchool getSubjectByNameAndSchool(School school, String subjectByName) {
        return subjectSchoolRepository.findBySchoolAndSubject(school,getSubjectByName(subjectByName)).orElse(null);
    }

    @Override
    public Subjects getSubjectById(String id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public int getSubjectCount() {
        return (int) subjectRepository.count();
    }

    @Override
    public void deleteSubject(String id) {
        this.subjectRepository.deleteById(id);
    }

    @Override
    public void removeSubjectSchoolsBySchoolId(String id) {
        subjectSchoolRepository.deleteBySchoolId(id);
    }


}


