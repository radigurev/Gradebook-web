package Gradebook.repository;

import Gradebook.model.entity.Role;
import Gradebook.model.entity.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role getByRole(String role);
}
