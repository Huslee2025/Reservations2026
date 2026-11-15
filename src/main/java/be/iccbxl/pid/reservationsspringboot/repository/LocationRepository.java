package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{
  Location findByDesignation(String designation);
}
