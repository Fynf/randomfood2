package dev.fynf.randomfood2.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(unique = true, nullable = false)
  private String foodName;

  public Food() {
  }

  public Food(String foodName) {
    this.foodName = foodName;
  }
}
