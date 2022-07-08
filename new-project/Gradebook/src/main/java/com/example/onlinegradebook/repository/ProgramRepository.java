package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program,String > {
}
