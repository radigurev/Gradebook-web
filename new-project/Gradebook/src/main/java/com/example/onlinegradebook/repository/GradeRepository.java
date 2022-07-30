package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Grades;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository  extends JpaRepository<Grades,String> {

    List<Grades> getAllByStudent(User user);
}
