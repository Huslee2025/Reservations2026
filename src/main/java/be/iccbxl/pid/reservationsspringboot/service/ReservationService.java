package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import be.iccbxl.pid.reservationsspringboot.repository.ReservationRepository;

@Service
public class ReservationService{

  private final ReservationRepository reservationRepository;

  //Injection du repository par constructeur
  public ReservationService(ReservationRepository reservationRepository){
    this.reservationRepository = reservationRepository;
  }

  //Récupérer toutes les reservations
  @Transactional(readOnly = true)
  public List<Reservation> getAll(){
    return reservationRepository.findAll();
  }

  //Récupérer une réservation par son ID
  @Transactional(readOnly = true)
  public Reservation getReservation(Integer id){
    return reservationRepository.findById(id).orElse(null);
  }

}
