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
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="reservations")
public class Reservation{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="user_id", nullable = false)
  private User user;

  @Column(name="booking_date", nullable=false)
  private LocalDateTime bookingDate;

  @Column(length=60, nullable = false)
  private String status;

  protected Reservation(){
  }

  public Reservation(User user, String status){
    setUser(user);
    this.bookingDate = LocalDateTime.now();
    this.status = status;
  }

  public void setUser(User user){

    if(this.user == user){
      return;
    }

    User previousUser = this.user;
    this.user = user;

    if(previousUser != null){
      previousUser.getReservations().remove(this);
    }

    if(user != null && !user.getReservations().contains(this)){
      user.getReservations().add(this);
    }
  }

  @Override
  public String toString(){
    return "Reservation [id=" + id
            + ",userId=" + (user != null ? user.getId() : null)
            + ", bookingDate=" + bookingDate
            + ", status=" + status + "]";
  }














}
