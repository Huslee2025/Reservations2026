package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Representation;
import be.iccbxl.pid.reservationsspringboot.service.RepresentationService;

@Controller
public class RepresentationController{

  private final RepresentationService representationService;

  //Injection par constructeur
  public RepresentationController(RepresentationService representationService){
    this.representationService = representationService;
  }

  //Liste des représentations
  @GetMapping("/representations")
  public String index(Model model){
    
    List<Representation> representations = representationService.getAll();

    model.addAttribute("representations",representations);
    model.addAttribute("title","Liste des représentations");

    return "representation/index";

  }

  //Fiche d'une représentation
  @GetMapping("/representations/{id}")
    public String show(@PathVariable("id") Long id, Model model){

    Representation representation = representationService.getRepresentation(id);

    if(representation == null){
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Représentation introuvable"
      );
    }

    model.addAttribute("representation",representation);
    model.addAttribute("date",representation.getWhen().toLocalDate());
    model.addAttribute("heure", representation.getWhen().toLocalTime());
    model.addAttribute("title","Fiche d'une représentation");

    return "representation/show";

  }

}
