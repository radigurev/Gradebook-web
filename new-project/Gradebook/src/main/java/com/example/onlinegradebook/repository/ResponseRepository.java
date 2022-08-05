package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.Response;
import com.example.onlinegradebook.model.entity.enums.ResponseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response,String> {
    Response findByType(ResponseType responseType);

}
