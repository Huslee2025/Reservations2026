package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist,Integer>{



	List<Artist> findByLastname(String lastname);



}
