package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.UsersSubjects;
import com.example.onlinegradebook.service.UsersSubjectsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsersSubjectsRepository extends JpaRepository<UsersSubjects, String> {

    Set<UsersSubjects> findAllByUser(User user);

    Optional<UsersSubjects> findByUserAndSubject(User user, Subjects subject);

    @Transactional
    void removeAllByUser(User user);
}
