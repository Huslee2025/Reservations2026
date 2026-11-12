package be.iccbxl.pid.reservationsspringboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Type;

public interface TypeRepository extends JpaRepository<Type,Long>{
  Type findByType(String type);
}
