package be.iccbxl.pid.reservationsspringboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.dto.UserProfileDto;
import be.iccbxl.pid.reservationsspringboot.dto.UserRegistrationDto;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {

    return userRepository.findByDeletedAtIsNull();

  }

  public User getUser(long id) {
    return userRepository.findByIdAndDeletedAtIsNull(id);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmailAndDeletedAtIsNull(email).orElse(null);
  }

  public boolean isLoginAndEmailAvailable(String login, String email){
    return !userRepository.existsByLogin(login) && !userRepository.existsByEmail(email);

  }

  public void registerFromDto(UserRegistrationDto dto){

    User user = new User();
    user.setFirstname(dto.getFirstname());
    user.setLastname(dto.getLastname());
    user.setLogin(dto.getLogin());
    user.setEmail(dto.getEmail());
    user.setLangue(dto.getLangue());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(UserRole.MEMBER);
    userRepository.save(user);

  }


  public void updateUser(long id, User user) {

    User activeUser = userRepository.findByIdAndDeletedAtIsNull(id);

    if(activeUser == null){
      throw new RuntimeException("Utilisateur introuvable");
    }

    if(user == null || user.getId() != id){
      throw new IllegalArgumentException("Impossible de mettre à jour le compte : utilisateur absent ou identifiant incorrect");
    }
    userRepository.save(user);
  }


  public void updateUserFromDto(UserProfileDto dto){
    User user = userRepository.findByIdAndDeletedAtIsNull(dto.getId());
    
    if(user == null){
      throw new RuntimeException("Utilisateur introuvable");
    }

    user.setFirstname(dto.getFirstname());
    user.setLastname(dto.getLastname());
    user.setEmail(dto.getEmail());
    user.setLangue(dto.getLangue());

    //Si un nouveau mot de passe est fourni et validé
    if(dto.getPassword() != null && !dto.getPassword().isBlank()){
      user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    userRepository.save(user);
  }

  public void deleteUser(long id) {
    User user = userRepository.findByIdAndDeletedAtIsNull(id);

    if(user != null){
      user.setDeletedAt(LocalDateTime.now());
      userRepository.save(user);
    }
  }

  public void deleteByLogin(String login){
    User user = userRepository.findByLogin(login);

    if(user != null && user.getDeletedAt() == null){
      user.setDeletedAt(LocalDateTime.now());
      userRepository.save(user);
    }
  }

}
