package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,String> {

    List<Test> getAllBySchoolOrderByDateDesc(School school);
}
