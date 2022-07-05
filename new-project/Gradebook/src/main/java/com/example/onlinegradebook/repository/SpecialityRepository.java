package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,String> {

    Speciality getSpecialityByName(String name);
}
