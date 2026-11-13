package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name="localities")
public class Locality{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(name="postal_code", length=6, nullable = false)
  private String postalCode;

  @Setter
  @Column(name = "locality", length=60, nullable = false)
  private String locality;

  protected Locality(){

  }

  public Locality(String postalCode, String locality){
    this.postalCode =postalCode;
    this.locality = locality;
  }

  @Override
  public String toString(){
    return "Locality [id=" + id
            + ", postalCode=" + postalCode
            + ", locality=" + locality + "]";
  }

}
