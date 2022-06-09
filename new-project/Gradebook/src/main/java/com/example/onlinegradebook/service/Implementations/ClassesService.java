package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.repository.ClassesRepository;
import com.example.onlinegradebook.service.ClassService;
import org.springframework.stereotype.Service;

@Service
public class ClassesService implements ClassService {

    private final ClassesRepository classesRepository;

    public ClassesService(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    @Override
    public Classes getClass(String number) {
        return classesRepository.findByClassNumber(number);
    }
}
