package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByLogin(String login);
  List<User> findByLastnameAndDeletedAtIsNull(String lastname);

  Optional<User> findByEmail(String email);

  User findByLoginAndDeletedAtIsNull(String login);
  List<User> findByDeletedAtIsNull();
  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  User findByIdAndDeletedAtIsNotNull(Integer id);
  List<User> findByDeletedAtIsNotNull();

  User findByIdAndDeletedAtIsNull(Integer id);

  boolean existsByEmail(String email);
  boolean existsByLogin(String login);

}
