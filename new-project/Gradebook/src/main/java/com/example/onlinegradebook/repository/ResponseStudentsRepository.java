package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.ResponseStudents;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseStudentsRepository extends JpaRepository<ResponseStudents, String> {

    List<ResponseStudents> findAllByStudent(User user);

    List<ResponseStudents> getAllBySchool(School school);
}
