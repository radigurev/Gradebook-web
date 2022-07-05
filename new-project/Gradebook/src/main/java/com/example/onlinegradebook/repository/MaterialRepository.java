package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material,String> {
    Optional<Material> findByName(String name);
}
