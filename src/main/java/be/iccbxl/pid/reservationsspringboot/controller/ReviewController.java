package be.iccbxl.pid.reservationsspringboot.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservationsspringboot.model.Review;
import be.iccbxl.pid.reservationsspringboot.service.ReviewService;

@Controller
@RequestMapping("/admin/reviews")
public class ReviewController{

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService){
    this.reviewService = reviewService;
  }

  @GetMapping
  public String index(Model model){

    List<Review> reviews = reviewService.getAll();

    model.addAttribute("reviews", reviews);

    return "review/index";

  }

  @GetMapping("/{id}")
  public String show(@PathVariable Integer id, Model model){

    Review review = reviewService.getReview(id);

    if(review == null){
      throw new ResponseStatusException(
      HttpStatus.NOT_FOUND,
      "Avis introuvable");
    }

    model.addAttribute("review", review);

    return "review/show";

  }

}
