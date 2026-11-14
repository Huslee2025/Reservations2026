package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

  Role findByRole(String role);

}
