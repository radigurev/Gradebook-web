package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grades,String> {
}
