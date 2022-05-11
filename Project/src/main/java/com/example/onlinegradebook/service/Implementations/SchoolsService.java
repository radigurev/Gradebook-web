package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.SchoolService;
import org.springframework.stereotype.Service;

@Service
public class SchoolsService implements SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolsService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School findBySchool(String school) {
        return schoolRepository.findBySchool(school).orElse(null);
    }
}
