package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);


    Collection<User> getAllBySchoolAndRoleIn(School school, Set<Role> roles);

}
