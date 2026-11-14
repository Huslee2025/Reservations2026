package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Role;
import be.iccbxl.pid.reservationsspringboot.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController{

  private final RoleService roleService;

  public RoleController(RoleService roleService){
    this.roleService = roleService;
  }

  //Afficher la liste de tous les rôles
  @GetMapping
  public String index(Model model){

    List<Role> roles = roleService.getAll();

    model.addAttribute("roles", roles);
    model.addAttribute("title","Liste des rôles");

    return "role/index";

  }

  //Afficher les informations d'un rôle précis
  @GetMapping("/{id}")
  public String show(@PathVariable("id") Long id, Model model){

    Role role = roleService.getRole(id);

    if(role == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Rôle introuvable"
      );
    }

    model.addAttribute("role", role);
    model.addAttribute("title","Fiche d'un rôle");

    return "role/show";
  }

}
