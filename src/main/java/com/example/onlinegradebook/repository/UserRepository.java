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

   /*@Transactional
    @Modifying
    @Query("UPDATE User u SET u.middleName = :middleName, u.birthDate = :birthDate, u.phoneNumber = :phoneNumber," +
            " u.address = :address , u.school = :school,u.city = :city, u.country = :country, u.zip = :zip"+
            " WHERE u.email = :email")  // u.ssn = :ssn, u.city = :city, u.country = :country,
    void fillUserInformation(@Param(value = "middleName") String middleName,@Param(value = "birthDate") Date birthDate
            ,@Param(value = "phoneNumber") String phoneNumber,@Param(value = "address") String address,@Param(value = "school") String school
                         //@Param(value = "ssn") String ssn
            ,@Param(value = "city") City city,@Param(value = "county") Country county,
                         @Param(value = "zip") Integer zip,@Param("email") String email);

    */
       /* @Transactional
        @Modifying
    @Query("UPDATE User u SET u.middleName = :middleName, u.birthDate = :birthDate, u.phoneNumber = :phoneNumber,u.address= :address, u.school=:school,u.city =:city WHERE u.email= :email")
    void fillUserInfo(@Param(value = "middleName") String middleName,@Param(value = "birthDate") Date birthDate
                ,@Param(value = "phoneNumber") String phoneNumber,@Param(value = "address") String address,
                      @Param(value = "school") String school,@Param(value = "City") long city,
                      @Param(value = "email") String email);

        */
    @Modifying
    @Query("UPDATE User u set u.city =?1 WHERE u.email = ?2")
    void update(@Param(value = "city") City city,@Param(value = "email") String email);

}
