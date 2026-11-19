package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import com.github.slugify.Slugify;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "locations")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

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

  @OneToMany(mappedBy = "location")
  private List<Show> shows = new ArrayList<>();

  @OneToMany(mappedBy = "location")
  private List<Representation> representations = new ArrayList<>();

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

  public Location addShow(Show show){

    if(show == null){
      throw new IllegalArgumentException("Le spectacle est obligatoire");
    }

    if(!this.shows.contains(show)){
      this.shows.add(show);
    }

    if(show.getLocation() != this){
      show.setLocation(this);
    }

    return this;
  }

  public Location removeShow(Show show){

    if(show == null){
      return this;
    }

    this.shows.remove(show);

    if(show.getLocation() == this){
      show.setLocation(null);
    }

    return this;
  }

  public Location addRepresentation(Representation representation){

    if(!this.representations.contains(representation)){
      this.representations.add(representation);
      representation.setLocation(this);
    }

    return this;
  }

  public Location removeRepresentation(Representation representation){

    if(this.representations.contains(representation)){
      this.representations.remove(representation);

      if(representation.getLocation() == this){
        representation.setLocation(null);
      }
    }

    return this;
  }


  @Override
  public String toString() {
    return "Location [id" + id
        + ", slug=" + slug
        + ", designation=" + designation
        + ", address=" + address
        + ", localityId=" + (locality != null ? locality.getId() : null)
        + ", website=" + website
        + ", phone=" + phone + "]"
        + ", shows=" + shows.size() + "]";
  }

}
