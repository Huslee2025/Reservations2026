package be.iccbxl.pid.reservationsspringboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "show_id", nullable = false)
  private Show show;

  @Column(columnDefinition = "TEXT")
  private String review;

  @NotNull
  @Min(1)
  @Max(5)
  @Column(nullable = false)
  private Byte stars;

  @Column(nullable = false)
  private boolean validated = false;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  protected Review(){
  }

  public Review(User user, Show show, String review, Byte stars){

    if(user == null || show == null){
      throw new IllegalArgumentException(
        "L'utilisateur et le spectacle sont obligatoires");
    }

    setStars(stars);
    setUser(user);
    setShow(show);
    this.review = review;
  }

  public void setUser(User user){

    if(user == null){
      throw new IllegalArgumentException(
        "L'utilisateur est obligatoire");
    }

    if(this.user == user){
      return;
    }

    User previousUser = this.user;
    this.user = user;

    if(previousUser != null){
      previousUser.getReviews().remove(this);
    }

    if(!user.getReviews().contains(this)){
      user.getReviews().add(this);
    }
  }

  public void setShow(Show show){

    if(show == null){
      throw new IllegalArgumentException(
        "Le spectacle est obligatoire");
    }

    if(this.show == show){
      return;
    }

    Show previousShow = this.show;
    this.show = show;

    if(previousShow != null){
      previousShow.getReviews().remove(this);
    }

    if(!show.getReviews().contains(this)){
      show.getReviews().add(this);

    }
  }

  public void setReview(String review){
    this.review = review;
  }

  public void setStars(Byte stars){

    if(stars == null || stars < 1 || stars > 5){
      throw new IllegalArgumentException(
        "La note doit être comprise entre 1 et 5"
      );
    }

    this.stars = stars;
  }

  public void setValidated(boolean validated){
    this.validated = validated;
  }

  @PrePersist
  protected void onCreate(){
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
  }

  @PreUpdate
  protected void onUpdate(){
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public String toString(){
    return "Review [id=" + id
        + ", userId=" + (user != null ? user.getId() : null)
        + ", showId=" + (show != null ? show.getId() : null)
        + ", review=" + review
        + ", stars=" + stars
        +", validated=" + validated
        + ", createdAt=" + createdAt
        + ", updatedAt=" + updatedAt + "]";
  }
}
