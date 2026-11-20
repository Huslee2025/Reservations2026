package be.iccbxl.pid.reservationsspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.slugify.Slugify;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "shows")
public class Show{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length=60, unique=true, nullable=false)
  private String slug;

  @Column(length=255, nullable=false)
  private String title;

  @Setter
  @Column(columnDefinition="LONGTEXT")
  private String description;

  @Setter
  @Column(name="poster_url", length = 255)
  private String posterUrl;

  //Lieu de création du spectacle
  @ManyToOne
  @JoinColumn(name="location_id", nullable = true)
  private Location location;

  @ManyToMany(mappedBy = "shows")
  private List<ArtistType> artistTypes = new ArrayList<>();

  @Setter
  @Column(nullable = false)
  private boolean bookable;

  @Setter
  @Column(precision = 10, scale=2)
  private BigDecimal price;

  //Date de création
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  //Date de modification
  @Setter
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy="show")
  private List<Representation> representations = new ArrayList<>();

  //Constructeur nécessaire à JPA
  protected Show(){
  }

  //Constructeur personnalisé
  public Show(String title, String description, String posterUrl, 
              Location location, boolean bookable,BigDecimal price){

    setTitle(title);
    this.description = description;
    this.posterUrl = posterUrl;
    setLocation(location);
    this.bookable = bookable;
    this.price = price;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
  }

  //Modifier le titre et recalculer le slug
  public void setTitle(String title){

    this.title = title;

    Slugify slugify = Slugify.builder().build();
    this.slug = slugify.slugify(title);
  }

  //Synchroniser la relation avec Location
  public void setLocation(Location location){

    if(this.location == location){
      return;
    }

    Location previousLocation = this.location;
    this.location = location;

    if(previousLocation != null){
      previousLocation.removeShow(this);
    }

    if(location != null){
      location.addShow(this);
    }
  }

  public Show addRepresentation(Representation representation){
      
    if(!this.representations.contains(representation)){
      this.representations.add(representation);
      representation.setShow(this);
    }

    return this;
  }

  public Show removeRepresentation(Representation representation){

    if(this.representations.contains(representation)){
      this.representations.remove(representation);

      if(representation.getShow() == this){
        representation.setShow(null);
      }
    }

    return this;
  }

  public Show addArtistType(ArtistType artistType){

    if(!this.artistTypes.contains(artistType)){
      this.artistTypes.add(artistType);
      artistType.addShow(this);
    }

    return this;
  }

  public Show removeArtistType(ArtistType artistType){

    if(this.artistTypes.contains(artistType)){
      this.artistTypes.remove(artistType);
      artistType.getShows().remove(this);
    }

    return this;
  }

  @Override
  public String toString(){
    return "Show [id=" + id
           +",slug=" + slug
           +",title=" + title
           +",description=" + description
           +",posterUrl=" + posterUrl
           +",locationId=" + (location != null ? location.getId() : null)
           +",bookable=" + bookable
           +",price=" + price
           +",createdAt=" + createdAt
           +",updatedAt=" + updatedAt + "]";
  }

}
