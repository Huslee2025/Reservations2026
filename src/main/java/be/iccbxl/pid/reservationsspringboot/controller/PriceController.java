package be.iccbxl.pid.reservationsspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Price;
import be.iccbxl.pid.reservationsspringboot.service.PriceService;

@Controller
@RequestMapping("/admin/prices")
public class PriceController{

  private final PriceService priceService;

  //Injection du service par constructeur
  public PriceController(PriceService priceService){
    this.priceService = priceService;
  }

  //Afficher tous les tarifs
  @GetMapping
  public String index(Model model){

    model.addAttribute("prices", priceService.getAll());

    return "price/index";

  }

  //Afficher un tarif précis
  @GetMapping("/{id}")
  public String show(@PathVariable Integer id, Model model){

    Price price = priceService.getPrice(id);

    if(price == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Tarif introuvable"
      );
    }

    model.addAttribute("price", price);

    return "price/show";

  }

}
