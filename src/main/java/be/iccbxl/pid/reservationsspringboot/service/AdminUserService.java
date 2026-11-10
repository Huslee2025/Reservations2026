package be.iccbxl.pid.reservationsspringboot.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

@Service
public class AdminUserService{

  private final UserRepository userRepository;

  public AdminUserService(UserRepository userRepository){
    this.userRepository =userRepository;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void updateUserRole(long id, UserRole newRole){

    //Vérifier que l'utilisateur existe et que son compte est actif
    User user = userRepository.findByIdAndDeletedAtIsNull(id);

    if(user == null){
      throw new IllegalArgumentException("Utilisateur introuvable");
    }

    //Récupérer le login de l'administrateur connecté
    String currentLogin=SecurityContextHolder.getContext()
          .getAuthentication()
          .getName();

    //Interdire la modification de son propre rôle
    if(user.getLogin().equals(currentLogin)){
      throw new AccessDeniedException(
                 "Impossible de modifier son propre rôle");
    }

    //Interdire la modification du rôle d'un compte administrateur
    if(user.getRole() == UserRole.ADMIN){
      throw new AccessDeniedException(
              "Impossible de modifier un administrateur"); 
    }

    //Vérifier que le nouveau rôle est autorisé
    if(newRole == null){
      throw new IllegalArgumentException(
              "Le nouveau rôle est obligatoire");
    }

    //Mettre à jour et enregistrer le nouveau rôle
    user.setRole(newRole);
    userRepository.save(user);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void deactivateUser(long id){

    //Vérifier que le compte existe et qu'il est actif
    User user = userRepository.findByIdAndDeletedAtIsNull(id);

    if(user == null){
      throw new IllegalArgumentException(
        "Utilisateur introuvable ou déjà désactivé"
      );    
    }

    //Récupérer le login de l'administrateur connecté
    String currentLogin = SecurityContextHolder.getContext()
    .getAuthentication()
    .getName();

    //Empêcher la déactivation de son propre compte
    if(user.getLogin().equals(currentLogin)){
      throw new AccessDeniedException(
        "Impossible de désactiver son propre compte"
      );
    }

    //Empêcher la désactivation d'un autre administrateur
    if(user.getRole() == UserRole.ADMIN){
      throw new AccessDeniedException(
      "Impossible de désactiver un administrateur"
      );
    }

    //Désactiver le compte sans le supprimer de la BD
    user.setDeletedAt(LocalDateTime.now());

    userRepository.save(user);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void reactivateUser(long id){

    User user = userRepository.findByIdAndDeletedAtIsNotNull(id);

    if(user == null){
      throw new IllegalArgumentException(
        "Utilisateur introuvable ou déjà actif"
      );
    }

    user.setDeletedAt(null);

    userRepository.save(user);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional(readOnly = true)
  public List<User> getDeactivatedUsers(){
    return userRepository.findByDeletedAtIsNotNull();
  }

}
