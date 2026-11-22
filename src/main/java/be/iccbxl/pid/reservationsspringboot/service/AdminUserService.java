package be.iccbxl.pid.reservationsspringboot.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Role;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.repository.RoleRepository;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

@Service
public class AdminUserService{

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public AdminUserService(UserRepository userRepository,
                          RoleRepository roleRepository){
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void updateUserRoles(Integer id, Set<UserRole> newRoles){

    //Vérifier que l'utilisateur existe et que son compte est actif
    User user = userRepository.findByIdAndDeletedAtIsNull(id);

    if(user == null){
      throw new IllegalArgumentException("Utilisateur introuvable");
    }

    //Récupérer le login de l'utilisateur connecté
    String currentLogin = SecurityContextHolder.getContext()
          .getAuthentication()
          .getName();

    //Vérifier si l'administrateur modifie son propre compte
    boolean isOwnAccount = user.getLogin().equals(currentLogin);

    //Interdire la modification d'un autre administrateur
    if(user.hasRole(UserRole.ADMIN) && !isOwnAccount){
      throw new AccessDeniedException(
       "Impossible de modifier un autre administrateur");
    }

    //Vérifier qu'au moins un rôle a été sélectionné
    if(newRoles == null || newRoles.isEmpty()){
      throw new IllegalArgumentException(
              "Au moins un rôle doit être sélectionné");
    }

    //Empêcher un administrateur de retirer son propre rôle ADMIN
    if(isOwnAccount && !newRoles.contains(UserRole.ADMIN)){
      throw new AccessDeniedException(
        "Impossible de retirer son propre rôle ADMIN");
    }

    //Récupérer tous les rôles sélectionnés depuis la BD
    List<Role> selectedRoles = new ArrayList<>();

    for(UserRole newRole : newRoles){

      Role role = roleRepository.findByRole(newRole.name());

      if(role == null){
        throw new IllegalStateException(
          "Rôle introuvable : " + newRole.name());
      }

      selectedRoles.add(role);
    }

    //Récupérer une copie des anciens rôles
    List<Role> oldRoles = new ArrayList<>(user.getRoles());

    //Retirer les rôles qui ne sont plus sélectionnés
    for(Role oldRole : oldRoles){

      if(!selectedRoles.contains(oldRole)){
        oldRole.removeUser(user);
      }
    }

    //Ajouter les nouveaux rôles
    for(Role selectedRole : selectedRoles){

      if(!user.getRoles().contains(selectedRole)){
        selectedRole.addUser(user);
      }
    }

    //Synchroniser provisoirement l'ancienne colonne users.role
    if(!newRoles.contains(user.getRole())){

      for(UserRole role : UserRole.values()){

        if(newRoles.contains(role)){
          user.setRole(role);
          break;
        }
      }
    }

    userRepository.save(user);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void deactivateUser(Integer id){

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
    if(user.hasRole(UserRole.ADMIN)){
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
  public void reactivateUser(Integer id){

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
