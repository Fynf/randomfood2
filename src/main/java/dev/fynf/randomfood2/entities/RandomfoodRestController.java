package dev.fynf.randomfood2.entities;

import dev.fynf.randomfood2.RandomfoodGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/")
public class RandomfoodRestController extends RandomfoodGenerator {

  @Autowired
  public RandomfoodRestController(FoodRepository foodRepository,
                                  ModifierRepository modifierRepository,
                                  ConnectorRepository connectorRepository) {
    super(foodRepository, modifierRepository, connectorRepository);
  }

  @GetMapping("{count}")
  public List<String> returnRandomFoods(@PathVariable int count) {
    if (count > 10) count = 10;

    return Stream.iterate(0, i -> i + 1)
        .limit(count)
        .map(i -> generateRandomfood())
        .collect(Collectors.toList());
  }

}
