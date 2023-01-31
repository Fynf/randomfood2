package dev.fynf.randomfood2.entities

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppetizerRepository : JpaRepository<Appetizer?, Long?>