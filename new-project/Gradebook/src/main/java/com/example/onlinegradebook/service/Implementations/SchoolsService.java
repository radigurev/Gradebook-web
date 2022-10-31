package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.view.SuperAdmin.AdminAndSchoolViewModel;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolsService implements SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolsService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School findSchool(String school) {
        return schoolRepository.findByName(school);
    }

    @Override
    public int getSchoolCount() {
        return (int) schoolRepository.count()-1;
    }

    @Override
    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    @Override
    public void deleteSchoolById(String id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public School findSchoolById(String id) {
        return schoolRepository.findById(id).orElse(null);
    }
}
