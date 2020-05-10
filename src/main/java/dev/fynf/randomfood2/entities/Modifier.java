package dev.fynf.randomfood2.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Modifier {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(unique = true, nullable = false)
  private String modifierName;

  public Modifier() {
  }

  public Modifier(String modifierName) {
    this.modifierName = modifierName;
  }
}
