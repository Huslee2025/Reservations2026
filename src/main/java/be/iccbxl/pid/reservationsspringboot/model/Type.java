package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name="types")
public class Type{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(name="type", length=60, nullable=false)
  private String type;

  @ManyToMany
  @JoinTable(
      name = "artist_type",
      joinColumns = @JoinColumn(name = "type_id"),
      inverseJoinColumns = @JoinColumn(name = "artist_id")
  )
  private List<Artist> artists = new ArrayList<>();

  protected Type(){}

  public Type(String type){
    this.type=type;
  }

  public Type addArtist(Artist artist){

    if(!this.artists.contains(artist)){
      this.artists.add(artist);
      artist.addType(this);
    }

    return this;
  }

  public Type removeArtist(Artist artist){

    if(this.artists.contains(artist)){
      this.artists.remove(artist);
      artist.getTypes().remove(this);
    }

    return this;
  }

  @Override
  public String toString(){
    return "Type [id=" +id + ",type=" + type +"]";
  }

}
