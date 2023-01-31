package dev.fynf.randomfood2.entities

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : CrudRepository<Food?, Long?> {
    fun findAllByIdNotIn(ids: List<Long?>?): List<Food?>?
}