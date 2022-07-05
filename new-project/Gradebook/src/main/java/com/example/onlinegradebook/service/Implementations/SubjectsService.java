package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.SubjectSchool;
import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.repository.SubjectRepository;
import com.example.onlinegradebook.repository.SubjectSchoolRepository;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectsService implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final SubjectSchoolRepository subjectSchoolRepository;
    public SubjectsService(SubjectRepository subjectRepository, @Lazy UserService userService, SubjectSchoolRepository subjectSchoolRepository) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.subjectSchoolRepository = subjectSchoolRepository;
    }

    @Override
    public List<Subjects> getAll() {

      return subjectSchoolRepository.getAllBySchool(userService.getUser().getSchool()).stream().map(SubjectSchool::getSubject).collect(Collectors.toList());
//        return subjectRepository.findAll();
    }

    @Override
    public Subjects getSubjectByName(String name) {
        return subjectRepository.getByName(name);
    }
}
