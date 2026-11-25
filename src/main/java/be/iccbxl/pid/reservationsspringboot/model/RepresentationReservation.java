package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name="representation_reservation")
public class RepresentationReservation{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="representation_id", nullable = false)
  private Representation representation;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="reservation_id", nullable = false)
  private Reservation reservation;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="price_id", nullable = false)
  private Price price;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Byte quantity;

  protected RepresentationReservation(){
  }

  public RepresentationReservation(
         Representation representation,
         Reservation reservation,
         Price price,
         Byte quantity){

    setRepresentation(representation);
    setReservation(reservation);
    setPrice(price);
    this. quantity = quantity;
  }

  public void setReservation(Reservation reservation){

    if(reservation == null){
      throw new IllegalArgumentException(
        "La réservation est obligatoire"
      );
    }

    if(this.reservation == reservation){
      return;
    }

    Reservation previousReservation = this.reservation;
    this.reservation = reservation;

    if(previousReservation != null){
      previousReservation.getRepresentationReservations().remove(this);
    }

    if(!reservation.getRepresentationReservations().contains(this)){
      reservation.getRepresentationReservations().add(this);
    }
  }

  public void setRepresentation(Representation representation){

    if(representation == null){
      throw new IllegalArgumentException(
        "La représentation est obligatoire"
      );
    }

    if(this.representation == representation){
      return;
    }

    Representation previousRepresentation = this.representation;
    this.representation = representation;

    if(previousRepresentation != null){
      previousRepresentation.getRepresentationReservations().remove(this);
    }

    if(!representation.getRepresentationReservations().contains(this)){
      representation.getRepresentationReservations().add(this);
    }
  }

  public void setPrice(Price price){

    if(price == null){
      throw new IllegalArgumentException(
        "Le tarif est obligatoire"
      );
    }

    if(this.price == price){
      return;
    }

    Price previousPrice = this.price;
    this.price = price;

    if(previousPrice != null){
      previousPrice.getRepresentationReservations().remove(this);
    }

    if(!price.getRepresentationReservations().contains(this)){
      price.getRepresentationReservations().add(this);
    }
  }

  @Override
  public String toString(){
    return "RepresentationReservation [id=" + id
           + ", representationId=" + (representation != null ? representation.getId() : null)
           + ", reservationId=" + (reservation != null ? reservation.getId() : null)
           + ", priceId=" + (price != null ? price.getId() : null)
           + ", quantity=" + quantity + "]";
  }

}
