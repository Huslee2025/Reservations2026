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
@Table(name="roles")
public class Role{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(name="role", length = 30, nullable = false)
  private String role;

  protected Role(){

  }

  public Role(String role){
    this.role=role;
  }

  @Override
  public String toString(){
    return "Role [id=" + id + ", role=" + role +"]";
  }
}
