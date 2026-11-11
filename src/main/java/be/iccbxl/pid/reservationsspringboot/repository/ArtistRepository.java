package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist,Long>{



	List<Artist> findByLastname(String lastname);

	Artist findById(long id);





}
