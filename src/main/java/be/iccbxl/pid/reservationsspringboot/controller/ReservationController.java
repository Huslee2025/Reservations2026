package be.iccbxl.pid.reservationsspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import be.iccbxl.pid.reservationsspringboot.service.ReservationService;

@Controller
@RequestMapping("/admin/reservations")
public class ReservationController{

  private final ReservationService reservationService;

  //Injection du service par constructeur
  public ReservationController(ReservationService reservationService){
    this.reservationService = reservationService;
  }

  //Afficher toutes les réservations
  @GetMapping
  public String index(Model model){

    model.addAttribute("reservations", reservationService.getAll());

    return "reservation/index";
  }

  //Afficher une réservation précise
  @GetMapping("/{id}")
  public String show(@PathVariable Integer id, Model model){

    Reservation reservation = reservationService.getReservation(id);

    if(reservation == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Réservation introuvable"
      );
    }

    model.addAttribute("reservation", reservation);

    return "reservation/show";
  }

}
