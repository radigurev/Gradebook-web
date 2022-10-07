package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SchoolRepository extends JpaRepository<School,String> {
        School findByName(String name);

        @Transactional
        void deleteById( String id);
}
