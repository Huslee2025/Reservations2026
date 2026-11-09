package be.iccbxl.pid.reservationsspringboot.controller;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import be.iccbxl.pid.reservationsspringboot.dto.AdminUserRoleDto;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.service.AdminUserService;
import be.iccbxl.pid.reservationsspringboot.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/admin/users")
public class AdminUserController{

  private final UserService userService;
  private final AdminUserService adminUserService;

  public AdminUserController(UserService userService,AdminUserService adminUserService){
    this.userService = userService;
    this.adminUserService = adminUserService;
  }

  //Afficher la liste des utilisateurs actifs
  @GetMapping
  public String index(Model model){

    model.addAttribute("users",userService.getAllUsers());

    return "admin/users/index";
  }

  //Afficher le formulaire de modification du rôle
  @GetMapping("/{id}/edit-role")
    public String editRole(@PathVariable long id,Model model) {
        
        User user = getEditableUser(id);

        AdminUserRoleDto dto = new AdminUserRoleDto();
        dto.setRole(user.getRole());

        model.addAttribute("roleForm",dto);
        model.addAttribute("userId",user.getId());
        model.addAttribute("userLogin",user.getLogin());
        model.addAttribute("roles",UserRole.values());

        return "admin/users/edit-role";
    }

  //Enregistrer le nouveau rôle
  @PostMapping("/{id}/edit-role")
    public String updateRole(
        @PathVariable long id,
        @Valid @ModelAttribute("roleForm") AdminUserRoleDto dto,
        BindingResult result,
        Model model,
        RedirectAttributes redirectAttributes) {

        if(result.hasErrors()){

      User user = getEditableUser(id);

      model.addAttribute("userId",user.getId());
      model.addAttribute("userLogin",user.getLogin());
      model.addAttribute("roles",UserRole.values());

      return "admin/users/edit-role";
    }

    try{adminUserService.updateUserRole(id,dto.getRole());
  
    }catch(IllegalArgumentException e){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Utilisateur introuvable", 
        e);
    }

    redirectAttributes.addFlashAttribute("successMessage","Rôle modifié avec succès");

    return "redirect:/admin/users";
  }

    //Retrouver un compte dont le rôle peut être modifié
    private User getEditableUser(long id) {

    User user = userService.getUser(id);

      if(user==null){
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Utilisateur introuvable");
      }

      if(user.getRole() == UserRole.ADMIN){
        throw new AccessDeniedException("Impossible de modifier le rôle d'un administrateur");
      }

      return user;

  }
}
