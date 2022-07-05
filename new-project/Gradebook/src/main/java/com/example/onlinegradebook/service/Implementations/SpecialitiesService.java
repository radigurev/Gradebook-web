package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Speciality;
import com.example.onlinegradebook.repository.SpecialityRepository;
import com.example.onlinegradebook.service.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialitiesService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialitiesService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public List<Speciality> getAll() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality getSpeciality(String speciality) {
        return specialityRepository.getSpecialityByName(speciality);
    }
}
