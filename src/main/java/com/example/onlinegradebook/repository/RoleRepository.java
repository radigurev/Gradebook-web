package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Roles;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,String> {
    Roles findByAccountType(AccountType name);
}
