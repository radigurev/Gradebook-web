package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Program;
import com.example.onlinegradebook.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program,String > {
    List<Program> findAllBySchool(School school);
}
