package dev.fynf.randomfood2.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectorRepository extends CrudRepository<Connector, Long> {
  List<Connector> findAllByIdNotIn(List<Long> ids);
}
