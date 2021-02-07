package dev.fynf.randomfood2.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Appetizer {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(unique = true, nullable = false)
  private String appetizerName;

  public Appetizer() {
  }

  public Appetizer(String appetizerName) {
    this.appetizerName = appetizerName;
  }
}
