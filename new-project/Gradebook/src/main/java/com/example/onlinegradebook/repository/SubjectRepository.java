package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, String> {

    Subjects getByName(String name);
}
