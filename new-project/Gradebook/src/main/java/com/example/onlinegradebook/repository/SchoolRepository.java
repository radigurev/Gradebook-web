package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School,String> {
        School findByName(String name);
}
