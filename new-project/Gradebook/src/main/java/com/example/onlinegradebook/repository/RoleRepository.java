package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByRole(Roles role);

}
