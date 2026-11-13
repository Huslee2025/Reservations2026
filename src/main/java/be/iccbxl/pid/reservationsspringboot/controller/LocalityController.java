package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Locality;
import be.iccbxl.pid.reservationsspringboot.service.LocalityService;

@Controller
@RequestMapping("/localities")
public class LocalityController{

  private final LocalityService localityService;

  public LocalityController(LocalityService localityService){
    this.localityService = localityService;
  }

  //Afficher la liste de toutes les localités
  @GetMapping
  public String index(Model model){

    List<Locality> localities = localityService.getAll();

    model.addAttribute("localities",localities);
    model.addAttribute("title","Liste des localités");

    return "locality/index";

  }

  //Afficher les informations d'une localité précise
  @GetMapping("/{id}")
  public String show(@PathVariable("id") Long id,Model model){

    Locality locality = localityService.getLocality(id);

    if(locality == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Localité introuvable"
      );
    }

    model.addAttribute("locality",locality);
    model.addAttribute("title","Fiche d'une localité");

    return "locality/show";

  }

}
