package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.SubjectSchool;
import com.example.onlinegradebook.model.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectSchoolRepository extends JpaRepository<SubjectSchool, String> {

    List<SubjectSchool> getAllBySchool(School school);

    Optional<SubjectSchool> findBySchoolAndSubject(School school, Subjects subject);

    @Transactional
    @Modifying
    @Query("DELETE FROM SubjectSchool c WHERE c.school.id =:id")
    void deleteBySchoolId(@Param(value = "id") String id);
}
