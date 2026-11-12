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
@Table(name="types")
public class Type{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(name="type", length=60, nullable=false)
  private String type;

  protected Type(){}

  public Type(String type){
    this.type=type;
  }

  @Override
  public String toString(){
    return "Type [id=" +id + ",type=" + type +"]";
  }

}
