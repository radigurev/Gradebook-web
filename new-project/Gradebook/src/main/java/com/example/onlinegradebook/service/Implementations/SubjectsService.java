package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.repository.ClassesSubjectsRepository;
import com.example.onlinegradebook.repository.SubjectRepository;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsService implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;

    private final ClassesSubjectsRepository classesSubjectsRepository;

    public SubjectsService(SubjectRepository subjectRepository, @Lazy UserService userService, ClassesSubjectsRepository classesSubjectsRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
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



}


