package dev.fynf.randomfood2.entities

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectorRepository : CrudRepository<Connector?, Long?> {
    fun findAllByIdNotIn(ids: List<Long?>?): List<Connector?>?
}