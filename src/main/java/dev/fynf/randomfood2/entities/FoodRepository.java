package dev.fynf.randomfood2.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {
  List<Food> findAllByIdNotIn(List<Long> ids);

}
