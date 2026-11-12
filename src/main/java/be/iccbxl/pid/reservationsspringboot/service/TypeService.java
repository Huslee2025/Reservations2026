package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.model.Type;
import be.iccbxl.pid.reservationsspringboot.repository.TypeRepository;

@Service
public class TypeService{
  @Autowired
  private TypeRepository typeRepository;

  public List<Type> getAll(){
    
    return typeRepository.findAll();
  }

  public  Type getType(Long id){

    Optional<Type> type = typeRepository.findById(id);

    return type.isPresent()?type.get():null;
  }

  public void addType(Type type){
    typeRepository.save(type);
  }

  public void updateType(Long id, Type updatedType){

    Type existingType = typeRepository.findById(id)
    .orElseThrow(()->
      new IllegalArgumentException("Type introuvable")
    );

    existingType.setType(updatedType.getType());

    typeRepository.save(existingType);
  }

  public void deleteType(Long id){

    typeRepository.deleteById(id);
  }

}
