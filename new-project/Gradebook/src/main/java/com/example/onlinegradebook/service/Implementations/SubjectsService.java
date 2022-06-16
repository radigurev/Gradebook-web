package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.repository.SubjectRepository;
import com.example.onlinegradebook.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsService implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectsService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subjects> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subjects getSubjectByName(String name) {
        return subjectRepository.getByName(name);
    }
}
