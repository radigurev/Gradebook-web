package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.MaterialSchool;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MaterialSchoolRepository extends JpaRepository<MaterialSchool,String> {
    List<MaterialSchool> getAllBySchoolAndTaken(School school, boolean taken);

    @Transactional
    @Modifying
    @Query("update MaterialSchool m set m.taken=:take,m.user=:user where m.id=:id")
    void takeMaterial(@Param(value = "take") boolean take, @Param(value = "user")User user, @Param(value = "id") String id);

}
