package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Location;
import be.iccbxl.pid.reservationsspringboot.model.Show;

public interface ShowRepository extends JpaRepository<Show,Long>{

  Show findBySlug(String slug);

  Show findByTitle(String title);

  List<Show> findByLocation(Location location);
}
