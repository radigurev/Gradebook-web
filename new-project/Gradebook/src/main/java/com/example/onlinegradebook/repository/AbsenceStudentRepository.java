package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.AbsenceStudent;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AbsenceStudentRepository extends JpaRepository<AbsenceStudent,String> {

    List<AbsenceStudent> getAbsenceStudentsByStudent(User user);
    @Transactional
    void deleteAbsenceStudentById(String id);

    List<AbsenceStudent> getAllBySchool(School school);
}
