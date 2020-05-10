package dev.fynf.randomfood2.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Connector {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(unique = true, nullable = false)
  private String connectorName;

  public Connector() {
  }

  public Connector(String connectorName) {
    this.connectorName = connectorName;
  }
}
