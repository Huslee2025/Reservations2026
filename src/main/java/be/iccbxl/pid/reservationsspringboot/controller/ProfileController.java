package be.iccbxl.pid.reservationsspringboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import be.iccbxl.pid.reservationsspringboot.dto.UserProfileDto;
import be.iccbxl.pid.reservationsspringboot.model.Language;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;
import be.iccbxl.pid.reservationsspringboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

  private final UserRepository userRepository;
  private final UserService userService;

  public ProfileController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  // Afficher la page de profil
  @GetMapping("/profile")
    public String showProfile(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String login = auth.getName();

    User user = userRepository.findByLoginAndDeletedAtIsNull(login);

    if(user == null){
      throw new RuntimeException("Utilisateur introuvable");
    }

    //Conversion User -> UserProfileDto
    UserProfileDto dto = new UserProfileDto();
    dto.setId(user.getId());
    dto.setFirstname(user.getFirstname());
    dto.setLastname(user.getLastname());
    dto.setEmail(user.getEmail());
    dto.setLangue(user.getLangue());
    dto.setLogin(user.getLogin());
    dto.setRole(user.getRole().getValue());

    //Conversion du code linguistique en nom de la langue 
    Language userLanguage = Arrays.stream(Language.values())
    .filter(lang -> lang.name().equalsIgnoreCase(user.getLangue()))
    .findFirst()
    .orElse(null);

    model.addAttribute("user_language", userLanguage!=null ? userLanguage.getDescription() : "Inconnue");
    model.addAttribute("user",dto);
     return "authentication/profile";
    }

  // Enregistrer les modifications
  @PostMapping(value="/profile", params={"edit"})

  public String updateProfile(@Valid @ModelAttribute("user") UserProfileDto dto,
      BindingResult result, Model model, RedirectAttributes redirAttrs) {

    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "Erreurs de validation!");
      return "authentication/profile";
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    String login = auth.getName();

    User currentUser = userRepository.findByLoginAndDeletedAtIsNull(login);
    if(currentUser == null){
      throw new RuntimeException("Utilisateur introuvable");
    }

    dto.setId(currentUser.getId());

    // Appel du service pour gérer mot de passe, etc.
    userService.updateUserFromDto(dto);
    redirAttrs.addFlashAttribute("successMessage", "Profil mis à jour avec succès !");
    return "redirect:profile";
  }

  @DeleteMapping("/profile/delete")
  public String deleteAccount(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirAttrs){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String login = auth.getName();

    if(request.isUserInRole("ADMIN")){
      redirAttrs.addFlashAttribute("errorMessage","Pas de suppression de son propre compte admin !");

      return "redirect:/profile";
    }

    //Supprimer l'utilisateur courant 
    userService.deleteByLogin(login);

    //Invalider la session HTTP (déconnecter l'utilisateur)
    /* Solution 1
    Http session =request.getSession(false);
    if(session != null){
      session.invalidate();
    }
    */ 

    //Variante plus Spring-Security "propre" (Solution 2)
    if(auth != null){
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    //Effacer le contexte de sécurité 
    SecurityContextHolder.clearContext();

    redirAttrs.addFlashAttribute("successMessage","Votre compte a été supprimé avec succès.");

    return "redirect:/";
  }

}
