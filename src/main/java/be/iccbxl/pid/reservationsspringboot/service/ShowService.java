package be.iccbxl.pid.reservationsspringboot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Location;
import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.repository.ShowRepository;

@Service
public class ShowService{

  private final ShowRepository showRepository;

  //Injection du repository par constructeur
  public ShowService(ShowRepository showRepository){
    this.showRepository = showRepository;
  }

  //Récupérer tous les spectacles
  public List<Show> getAll(){
    return showRepository.findAll();
  }

  //Récupérer un spectacle par son identifiant
  public Show getShow(Long id){
    return showRepository.findById(id).orElse(null);
  }

  //Ajouter un nouveau spectacle
  public void addShow(Show show){
    showRepository.save(show);
  }

  //Modifier un spectacle existant
  @Transactional
  public void updateShow(Long id, Show updatedShow){

    Show existingShow = showRepository.findById(id)
              .orElseThrow(() -> 
                  new IllegalArgumentException(
                      "Spectacle introuvable : " + id));

    existingShow.setTitle(updatedShow.getTitle());
    existingShow.setDescription(updatedShow.getDescription());
    existingShow.setPosterUrl(updatedShow.getPosterUrl());
    existingShow.setLocation(updatedShow.getLocation());
    existingShow.setBookable(updatedShow.isBookable());
    existingShow.setPrice(updatedShow.getPrice());
    existingShow.setUpdatedAt(LocalDateTime.now());

    showRepository.save(existingShow);
  }

  //Supprimer un spectacle
  public void deleteShow(Long id){
    showRepository.deleteById(id);
  }

  //Récupérer les spectacles associés à un lieu
  public List<Show> getFromLocation(Location location){
    return showRepository.findByLocation(location);
  }
}
