package dev.fynf.randomfood2.entities

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ModifierRepository : CrudRepository<Modifier?, Long?> {
    fun findAllByIdNotIn(ids: List<Long?>?): List<Modifier?>?
}