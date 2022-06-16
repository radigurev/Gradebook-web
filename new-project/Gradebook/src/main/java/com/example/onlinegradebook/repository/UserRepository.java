package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Collection<User> getAllBySchoolAndRoleIn(School school, Set<Role> roles);

    @Transactional
    @Modifying
    @Query("update User u set u.userClass=:class where u.id=:id")
    void updateClass(@Param(value = "id") String id, @Param(value = "class") Classes classes);
}
