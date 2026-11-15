package be.iccbxl.pid.reservationsspringboot.model;

import com.github.slugify.Slugify;

import jakarta.persistence.Column;
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
@Table(name = "locations")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 60, unique = true, nullable = false)
  private String slug;

  @Column(length = 60, nullable = false)
  private String designation;

  @Setter
  @Column(length = 255, nullable = false)
  private String address;

  @ManyToOne
  @JoinColumn(name = "locality_id", nullable = false)
  private Locality locality;

  @Setter
  @Column(length = 255)
  private String website;

  @Setter
  @Column(length = 30)
  private String phone;

  // Constructeur obligatoire pour JPA
  protected Location() {
  }

  // Constructeur
  public Location(String designation, String address,
      Locality locality, String website, String phone) {

    setDesignation(designation);
    this.address = address;
    setLocality(locality);
    this.website = website;
    this.phone = phone;

  }

  // Modifier la désignation et générer automatiquement le slug
  public void setDesignation(String designation) {

    this.designation = designation;

    Slugify slugify = Slugify.builder().build();
    this.slug = slugify.slugify(designation);
  }

  // Modifier la localité en synchronisant les deux cotés de la relation
  public void setLocality(Locality locality) {

    if (this.locality == locality) {
      return;
    }

    Locality previosuLocality = this.locality;
    this.locality = locality;

    if (previosuLocality != null) {
      previosuLocality.removeLocation(this);
    }

    if (locality != null) {
      locality.addLocation(this);
    }
  }

  @Override
  public String toString() {
    return "Location [id" + id
        + ", slug=" + slug
        + ", designation=" + designation
        + ", address=" + address
        + ", localityId=" + (locality != null ? locality.getId() : null)
        + ", website=" + website
        + ", phone=" + phone + "]";
  }

}
