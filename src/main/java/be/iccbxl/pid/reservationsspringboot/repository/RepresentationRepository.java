package be.iccbxl.pid.reservationsspringboot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Location;
import be.iccbxl.pid.reservationsspringboot.model.Representation;
import be.iccbxl.pid.reservationsspringboot.model.Show;

public interface RepresentationRepository extends JpaRepository<Representation, Long>{
  List<Representation> findByShow(Show show);
  List<Representation> findByLocation(Location location);
  List<Representation> findByWhen(LocalDateTime when);
}
