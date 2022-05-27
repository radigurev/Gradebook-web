package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country> findByCountry(String name);
}
