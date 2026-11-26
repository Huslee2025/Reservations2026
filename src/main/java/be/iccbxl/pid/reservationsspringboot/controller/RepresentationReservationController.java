package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.RepresentationReservation;
import be.iccbxl.pid.reservationsspringboot.service.RepresentationReservationService;

@Controller
@RequestMapping("/admin/representation-reservations")
public class RepresentationReservationController{

  private final RepresentationReservationService service;

  public RepresentationReservationController(
          RepresentationReservationService service){
    this.service = service;
  }

  @GetMapping
  public String index(Model model){

    List<RepresentationReservation> lignes = service.getAll();

    model.addAttribute("representationReservations", lignes);

    return "representationReservation/index";

  }

  @GetMapping("/{id}")
  public String show(@PathVariable Integer id, Model model){

    RepresentationReservation ligne = service.getRepresentationReservation(id);

    if(ligne == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Ligne de réservation introuvable");
    }

    model.addAttribute("representationReservation",ligne);

    return "representationReservation/show";

  }









}
