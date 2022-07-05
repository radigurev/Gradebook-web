package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.MaterialSchool;
import com.example.onlinegradebook.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassesSchoolRepository extends JpaRepository<ClassesSchool, String> {
    List<ClassesSchool> findAllByClassesAndSchool( Classes classes,School school);
    List<ClassesSchool> findAllBySchool(School school);

    @Query("select c from ClassesSchool c WHERE c.speciality.name=:speciality and c.classes.classNumber=:classes")
    List<ClassesSchool> findByClassesAndSubject(@Param(value = "speciality") String speciality, @Param(value = "classes") String classes);
}
