package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Price;

public interface PriceRepository extends JpaRepository<Price, Integer>{

}
