package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.service.ShowService;

@Controller
@RequestMapping("/shows")
public class ShowController{

  private final ShowService showService;

  //Injection du service par constructeur
  public ShowController(ShowService showService){
    this.showService = showService;
  }

  //Afficher la liste des spectacles
  @GetMapping
  public String index(Model model){

    List<Show> shows = showService.getAll();

    model.addAttribute("shows", shows);
    model.addAttribute("title", "Liste des spectacles");

    return "show/index";
  }

  //Afficher la fiche d'un spectacle
  @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){

    Show show = showService.getShow(id);

    if(show == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,"Spectacle introuvable");
    }

    model.addAttribute("show",show);
    model.addAttribute("title","Fiche d'un spectacle");

    return "show/show";

  }
}
