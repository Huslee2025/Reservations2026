package be.iccbxl.pid.reservationsspringboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name="representations")
public class Representation{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="show_id", nullable=false)
  private Show show;

  @Setter
  private LocalDateTime when;

  @ManyToOne
  @JoinColumn(name="location_id", nullable=true)
  private Location location;

  //Constructeur nécessaire à JPA
  public Representation(){
  }

  //Constructeur personnalisé
  public Representation(Show show, LocalDateTime when, Location location){
    this.show = show;
    this.when = when;
    this.location = location;
  }

  //Modifier le spectacle
  public void setShow(Show show){
    this.show =show;
  }

  //Modifier le lieu
  public void setLocation(Location location){
    this.location = location;
  }

  @Override
  public String toString(){
    return "Representation [id=" + id
            + ", show=" + show
            + ", when=" + when
            + ", location=" + location + "]";
  }

}

