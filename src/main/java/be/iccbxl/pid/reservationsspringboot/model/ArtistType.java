package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "artist_type")
public class ArtistType{

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Setter
  @ManyToOne
  @JoinColumn(name="artist_id", nullable = false)
  private Artist artist;

  @Setter
  @ManyToOne
  @JoinColumn(name="type_id",nullable=false)
  private Type type;

  @ManyToMany(targetEntity = Show.class)
  @JoinTable(
      name = "artist_type_show",
      joinColumns=@JoinColumn(name="artist_type_id"),
      inverseJoinColumns=@JoinColumn(name="show_id")
  )

  private List<Show> shows = new ArrayList<>();

  protected ArtistType(){
  }

  public ArtistType(Artist artist, Type type){
    this.artist = artist;
    this.type = type;
  }

  public ArtistType addShow(Show show){

    if(!this.shows.contains(show)){
      this.shows.add(show);
      show.addArtistType(this);
    }

    return this;
  }

  public ArtistType removeShow(Show show){

    if(this.shows.contains(show)){
      this.shows.remove(show);
      show.getArtistTypes().remove(this);
    }

    return this;
  }

  @Override
  public String toString(){
    return "ArtistType [id=" +id
            + ", artist=" + artist
            + ", type=" + type + "]";
  }


}
