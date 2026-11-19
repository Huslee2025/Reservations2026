package be.iccbxl.pid.reservationsspringboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Type;

public interface TypeRepository extends JpaRepository<Type,Integer>{
  Type findByType(String type);
}
