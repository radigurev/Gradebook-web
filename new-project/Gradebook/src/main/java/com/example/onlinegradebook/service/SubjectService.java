package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.Subjects;

import java.util.List;

public interface SubjectService {
    List<Subjects> getAll();

    Subjects getSubjectByName(String name);

    void saveSubject(String subject);

    Subjects getSubjectById(String id);

    int getSubjectCount();

    void deleteSubject(String id);
}
