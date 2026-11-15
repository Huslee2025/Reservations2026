package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Location;
import be.iccbxl.pid.reservationsspringboot.repository.LocationRepository;

@Service
public class LocationService{

  private final LocationRepository repository;

  //Injection du repository par constructeur
  public LocationService(LocationRepository repository){
    this.repository = repository;
  }

  //Récupérer tous les lieux
  public List<Location> getAll(){
    return repository.findAll();
  }

  //Récupérer un lieu par son identifiant
  public Location getLocation(Long id){
    return repository.findById(id).orElse(null);
  }

  //Ajouter un nouveau lieu
  public void addLocation(Location location){
    repository.save(location);
  }

  //Modifier un lieu existant
  @Transactional
  public void updateLocation(Long id, Location updatedLocation){

    Location existingLocation = repository.findById(id)
        .orElseThrow(() -> 
            new IllegalArgumentException("Lieu introuvable : " + id));

    existingLocation.setDesignation(updatedLocation.getDesignation());
    existingLocation.setAddress(updatedLocation.getAddress());
    existingLocation.setLocality(updatedLocation.getLocality());
    existingLocation.setWebsite(updatedLocation.getWebsite());
    existingLocation.setPhone(updatedLocation.getPhone());

    repository.save(existingLocation);
  }

  //Supprimer un lieu
  public void deleteLocation(Long id){
    repository.deleteById(id);
  }
}
