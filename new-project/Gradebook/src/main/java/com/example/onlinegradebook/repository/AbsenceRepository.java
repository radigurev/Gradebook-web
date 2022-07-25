package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Absence;
import com.example.onlinegradebook.model.entity.enums.AbsenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,String> {
    Absence findByType(AbsenceType absenceType);
}
