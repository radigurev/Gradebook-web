package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.SubjectSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectSchoolRepository extends JpaRepository<SubjectSchool, String> {

    List<SubjectSchool> getAllBySchool(School school);
}
