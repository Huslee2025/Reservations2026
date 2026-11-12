package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import be.iccbxl.pid.reservationsspringboot.model.Type;
import be.iccbxl.pid.reservationsspringboot.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;



@Controller
@RequestMapping("/types")
public class TypeController{

  private final TypeService typeService;

  public TypeController(TypeService typeService){
    this.typeService=typeService;
  }

  //Afficher la liste de tous les types
  @GetMapping
    public String index(Model model){
    List<Type> types = typeService.getAll();

    model.addAttribute("types",types);
    model.addAttribute("title","Liste des types");

    return "type/index";

    }

  //Afficher les informations d'un type précis
  @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id,Model model) {
        
    Type type = typeService.getType(id);

    if(type == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Type introuvable"
      );
    }

    model.addAttribute("type",type);
    model.addAttribute("title","Fiche d'un type");

    return "type/show";

    }

}
