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

    List<User> getAllBySchoolAndRoleIn(School school, Set<Role> roles);
    List<User> getAllBySchoolAndUserClassAndRoleInOrderByFirstName(School school, ClassesSchool classes, Set<Role> roles);

    List<User> getAllByRoleIn(Set<Role> roles);

    User getUserBySchoolAndMainAdmin(School school,boolean mainAdmin);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.school.id = :id")
    void deleteAdminsBySchool(@Param(value = "id") String id);
    @Transactional
    @Modifying
    @Query("update User u set u.role=:role where u.id=:id")
    void updateRoles(@Param(value = "role") Set<Role> role,@Param(value = "id") String id);

    @Transactional
    @Modifying
    @Query("update User u set u.userClass=:class where u.id=:id")
    void updateClass(@Param(value = "id") String id, @Param(value = "class") ClassesSchool classes);

    @Transactional
    @Modifying
    @Query("update User u set u.school=:school where u.id=:id")
    void updateSchool(@Param(value = "school") School school,@Param(value = "id") String id);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName =:firstName, u.middleName =:middleName, u.lastName =:lastName, u.email =:email, u.phoneNumber =:number where u.id =:id")
    void updateUser(@Param(value = "firstName") String firstName, @Param(value = "middleName") String middleName,
                    @Param(value = "lastName") String lastName, @Param(value = "email") String email,
                    @Param(value = "number") String number, @Param(value = "id") String id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.image =:image WHERE u.id =:id")
    void updateUserImage(@Param(value = "image") String image,@Param(value = "id") String id);
}
