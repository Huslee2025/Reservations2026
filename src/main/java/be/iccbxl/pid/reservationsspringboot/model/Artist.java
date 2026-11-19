package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "artists")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "The firstname must not be empty.")
  @Size(min = 2, max = 60, message = "The firstname must be between 2 and 60 characters long.")
  private String firstname;

  @NotBlank(message = "The lastname must not be empty.")
  @Size(min = 2, max = 60, message = "The lastname must be between 2 and 60 characters long.")
  private String lastname;

  @ManyToMany(mappedBy = "artists")
  @Setter(AccessLevel.NONE)
  private List<Type> types = new ArrayList<>();


  public Artist(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public Artist addType(Type type){

    if(!this.types.contains(type)){
      this.types.add(type);
      type.addArtist(this);
    }

    return this;
  }

  public Artist removeType(Type type){

    if(this.types.contains(type)){
      this.types.remove(type);
      type.getArtists().remove(this);
    }

    return this;
  }

  @Override
  public String toString() {
    return firstname + " " + lastname;
  }

}
