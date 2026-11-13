package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Locality;

public interface LocalityRepository extends JpaRepository<Locality,Long>{
  Locality findByPostalCode(String postalCode);
  Locality findByLocality(String locality);
}
