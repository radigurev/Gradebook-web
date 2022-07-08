package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.ClassesSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface ClassesSubjectsRepository extends JpaRepository<ClassesSubjects,String> {

}
