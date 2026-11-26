package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.RepresentationReservation;

public interface RepresentationReservationRepository extends JpaRepository<RepresentationReservation, Integer>{

  @EntityGraph(attributePaths = {"representation", "reservation", "price"})
  List<RepresentationReservation> findAllByOrderByReservation_IdAscIdAsc();

  @Override
  @EntityGraph(attributePaths = {"representation", "reservation", "price"})
  Optional<RepresentationReservation> findById(Integer id);

}
