package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.model.RepresentationReservation;
import be.iccbxl.pid.reservationsspringboot.repository.RepresentationReservationRepository;

@Service
public class RepresentationReservationService{

  private final RepresentationReservationRepository repository;

  public RepresentationReservationService(
          RepresentationReservationRepository repository){
    this.repository = repository;
  }

  public List<RepresentationReservation> getAll(){
    return repository.findAllByOrderByReservation_IdAscIdAsc();
  }

  public RepresentationReservation getRepresentationReservation(Integer id){

    return repository.findById(id).orElse(null);
  }

}
