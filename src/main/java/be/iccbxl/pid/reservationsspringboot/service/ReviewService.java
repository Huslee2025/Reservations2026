package be.iccbxl.pid.reservationsspringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.model.Review;
import be.iccbxl.pid.reservationsspringboot.repository.ReviewRepository;

@Service
public class ReviewService{

  private final ReviewRepository reviewRepository;

  public ReviewService(ReviewRepository reviewRepository){
    this.reviewRepository = reviewRepository;
  }

  public List<Review> getAll(){
    return reviewRepository.findAllByOrderByValidatedAscCreatedAtDesc();
  }

  public Review getReview(Integer id){
      return reviewRepository.findById(id).orElse(null);
  }

}
