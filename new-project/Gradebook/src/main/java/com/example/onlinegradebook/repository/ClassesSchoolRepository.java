package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassesSchoolRepository extends JpaRepository<ClassesSchool, String> {

    Optional<ClassesSchool> findByClassesAndSchool(Classes classes, School school);
}
