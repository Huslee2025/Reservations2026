package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "localities")
public class Locality {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Setter
  @Column(name = "postal_code", length = 6, nullable = false)
  private String postalCode;

  @Setter
  @Column(name = "locality", length = 60, nullable = false)
  private String locality;

  // Relation OneToMany avec Location
  @OneToMany(mappedBy = "locality")
  private List<Location> locations = new ArrayList<>();

  // Contructeur pour JPA
  protected Locality() {

  }

  public Locality(String postalCode, String locality) {
    this.postalCode = postalCode;
    this.locality = locality;
  }

  public Locality addLocation(Location location) {

    if (location == null) {
      throw new IllegalArgumentException("Le lieu est obligatoire");
    }

    if (!this.locations.contains(location)) {
      this.locations.add(location);
    }

    if (location.getLocality() != this) {
      location.setLocality(this);
    }

    return this;

  }

  public Locality removeLocation(Location location) {

    if (location == null) {
      return this;
    }

    this.locations.remove(location);

    if (location.getLocality() == this) {
      location.setLocality(null);
    }

    return this;
  }

  @Override
  public String toString() {
    return "Locality [id=" + id
        + ", postalCode=" + postalCode
        + ", locality=" + locality + "]";
  }

}
