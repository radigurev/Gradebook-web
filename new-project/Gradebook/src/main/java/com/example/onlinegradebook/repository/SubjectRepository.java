package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, String> {
    Optional<Subjects> getByName(String name);
}
