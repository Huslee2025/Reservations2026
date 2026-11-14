package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Role;
import be.iccbxl.pid.reservationsspringboot.repository.RoleRepository;

@Service
public class RoleService{

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
  }

  //Récupérer tous les rôles
  public List<Role> getAll(){
    return roleRepository.findAll();
  }

  //Récupérer un rôle par son identifiant
  public Role getRole(Long id){
    return roleRepository.findById(id).orElse(null);
  }

  //Récupérer un rôle par son nom
  public Role findByRole(String role){
    return roleRepository.findByRole(role);
  }

  //Ajouter un nouveau rôle
  public void addRole(Role role){
    roleRepository.save(role);
  }

  //Modifier un rôle existant
  @Transactional
  public void updateRole(Long id, Role updatedRole){

    Role existingRole = roleRepository.findById(id)
          .orElseThrow(()->
              new IllegalArgumentException("Rôle introuvable"));

    existingRole.setRole(updatedRole.getRole());

    roleRepository.save(existingRole);

  }

  //Supprimer un rôle
  @Transactional
  public void deleteRole(Long id){

    if(!roleRepository.existsById(id)){
      throw new IllegalArgumentException("Rôle introuvable");
    }

    roleRepository.deleteById(id);

  }
}

