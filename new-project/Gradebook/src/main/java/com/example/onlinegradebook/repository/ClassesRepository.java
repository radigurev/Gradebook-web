package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,String> {
    Classes findByClassNumber(String number);
}
