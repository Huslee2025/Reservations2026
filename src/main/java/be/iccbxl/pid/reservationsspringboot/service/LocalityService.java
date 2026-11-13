package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;
import org.springframework.stereotype.Service;
import be.iccbxl.pid.reservationsspringboot.model.Locality;
import be.iccbxl.pid.reservationsspringboot.repository.LocalityRepository;

@Service
public class LocalityService{

  private final LocalityRepository localityRepository;

  public LocalityService(LocalityRepository localityRepository){
    this.localityRepository = localityRepository;
  }

  //Récupérer toutes les localités
  public List<Locality> getAll(){
    return localityRepository.findAll();
  }

  //Récupérer une localité par son identifiant
  public Locality getLocality(Long id){
    return localityRepository.findById(id).orElse(null);
  }

  //Ajouter une nouvelle localité
  public void addLocality(Locality locality){
    localityRepository.save(locality);
  }

  //Modifier une localité existante
  public void updateLocality(Long id, Locality updatedLocality){

    Locality existingLocality= localityRepository.findById(id)
                    .orElseThrow(()->
                      new IllegalArgumentException("Localité introuvable"));

    existingLocality.setPostalCode(updatedLocality.getPostalCode());
    existingLocality.setLocality(updatedLocality.getLocality());

    localityRepository.save(existingLocality);
  }

  //Supprimer une localité
  public void deleteLocality(Long id){
    localityRepository.deleteById(id);
  }

}
