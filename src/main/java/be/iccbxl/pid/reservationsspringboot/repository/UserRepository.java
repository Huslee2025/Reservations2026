package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.reservationsspringboot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByLogin(String login);
  List<User> findByLastnameAndDeletedAtIsNull(String lastname);

  User findById(long id);
  Optional<User> findByEmail(String email);

  User findByLoginAndDeletedAtIsNull(String login);
  List<User> findByDeletedAtIsNull();
  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  User findByIdAndDeletedAtIsNull(long id);

  boolean existsByEmail(String email);
  boolean existsByLogin(String login);

}
