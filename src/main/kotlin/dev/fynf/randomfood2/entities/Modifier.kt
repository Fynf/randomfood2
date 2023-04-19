package dev.fynf.randomfood2.entities

import jakarta.persistence.*

@Entity
class Modifier(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long,

    @Column(unique = true, nullable = false) val modifierName: String

)