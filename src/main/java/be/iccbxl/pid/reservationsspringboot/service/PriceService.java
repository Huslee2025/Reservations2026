package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Price;
import be.iccbxl.pid.reservationsspringboot.repository.PriceRepository;

@Service
public class PriceService{

  private final PriceRepository priceRepository;

  //Injection du repository par constructeur
  public PriceService(PriceRepository priceRepository){
    this.priceRepository = priceRepository;
  }

  //Récupérer tous les tarifs
  @Transactional(readOnly = true)
  public List<Price> getAll(){
    return priceRepository.findAll();
  }

  //Récupérer un tarif par son ID
  @Transactional(readOnly = true)
  public Price getPrice(Integer id){
    return priceRepository.findById(id).orElse(null);
  }

  //Ajouter un nouveau tarif
  @Transactional
  public void addPrice(Price price){
    priceRepository.save(price);
  }

}
