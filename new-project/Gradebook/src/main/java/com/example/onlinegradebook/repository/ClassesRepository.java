package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,String> {
    Optional<Classes> findByClassNumber(String number);
}
