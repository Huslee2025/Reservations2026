package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

  @EntityGraph(attributePaths = {"user", "show"})
  List<Review> findAllByOrderByValidatedAscCreatedAtDesc();

  @Override
  @EntityGraph(attributePaths = {"user", "show"})
  Optional<Review> findById(Integer id);

}
