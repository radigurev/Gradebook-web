package com.example.onlinegradebook.repository;

import com.example.onlinegradebook.model.entity.City;
import com.example.onlinegradebook.model.entity.Country;
import com.example.onlinegradebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.birthDate = ?1, u.middleName=?2,u.phoneNumber=?3,u.school=?4,u.ssn=?5,u.address=?6,u.zip=?7,u.city=?8,u.country=?9 WHERE u.email = ?10")
    void updateUserInformation (Date birthDate,String middleName,String phoneNumber,String school,String snn,String address,Integer zip,City city,Country country,String email);

}
