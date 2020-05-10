package dev.fynf.randomfood2.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModifierRepository extends CrudRepository<Modifier, Long> {
  List<Modifier> findAllByIdNotIn(List<Long> ids);

}
