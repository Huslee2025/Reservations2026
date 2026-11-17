package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.model.Location;
import be.iccbxl.pid.reservationsspringboot.model.Representation;
import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.repository.RepresentationRepository;

@Service
public class RepresentationService{

  private final RepresentationRepository representationRepository;

  //Injection par constructeur
  public RepresentationService(RepresentationRepository representationRepository){
    this.representationRepository=representationRepository;
  }

  //Récupérer toutes les représentations
  public List<Representation> getAll(){
    return representationRepository.findAll();
  }

  //Récupérer une représentation par son ID
  public Representation getRepresentation(Long id){
    return representationRepository.findById(id).orElse(null);
  }

  //Ajouter une représentation
  public void addRepresentation(Representation representation){
    representationRepository.save(representation);
  }

  //Modifier une représentation
  public void updateRepresentation(Long id,Representation representation){
    representationRepository.save(representation);
  }

  //Supprimer une représentation
  public void deleteRepresentation(Long id){
    representationRepository.deleteById(id);
  }

  //Représentations d'un lieu
  public List<Representation> getFromLocation(Location location){
    return representationRepository.findByLocation(location);
  }

  //Représentations d'un spectacle
  public List<Representation> getFromShow(Show show){
    return representationRepository.findByShow(show);
  }
}
