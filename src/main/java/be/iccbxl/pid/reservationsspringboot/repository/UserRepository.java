package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  
  User findByLogin(String login);

  List<User> findByLastnameAndDeletedAtIsNull(String lastname);

  Optional<User> findByEmail(String email);

  @EntityGraph(attributePaths = "roles")
  User findByLoginAndDeletedAtIsNull(String login);

  @EntityGraph(attributePaths = "roles")
  List<User> findByDeletedAtIsNull();

  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  User findByIdAndDeletedAtIsNotNull(Integer id);

  @EntityGraph(attributePaths = "roles")
  List<User> findByDeletedAtIsNotNull();

  @EntityGraph(attributePaths = "roles")
  User findByIdAndDeletedAtIsNull(Integer id);

  boolean existsByEmail(String email);
  boolean existsByLogin(String login);

}
