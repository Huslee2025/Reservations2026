package be.iccbxl.pid.reservationsspringboot.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Entity
@Table(name="prices")
public class Price{

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(length=30, nullable=false)
  private String type;

  @Column(precision=10, scale=2, nullable=false)
  private BigDecimal price;

  @Column(name="start_date")
  private LocalDate startDate;

  @Column(name="end_date")
  private LocalDate endDate;

  protected Price(){
  }

  public Price(String type, BigDecimal price,
              LocalDate startDate, LocalDate endDate){

    this.type = type;
    this.price = price;
    this.startDate = startDate;
    this.endDate = endDate;
  }

}
