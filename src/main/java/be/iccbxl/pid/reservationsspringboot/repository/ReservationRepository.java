package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

  @Override
  @EntityGraph(attributePaths = "user")
  List<Reservation> findAll();

  @Override
  @EntityGraph(attributePaths = "user")
  Optional<Reservation> findById(Integer id);

}
