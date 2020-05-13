package dev.fynf.randomfood2.entities;

import dev.fynf.randomfood2.RandomfoodGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

  /**
   * Generiert eine Liste von {count} randomfoods und gibt diese als HTML-Response zurück.
   * Die Liste ist in ihrer Größe auf maximal 10 beschränkt,
   * höhere Anfragen und Zahlen < 1 werden mit HTML-Forbidden geahndet.
   *
   * @param count Die vom Nutzer in der URL angegebene, gewünschte Anzahl von Randomfoods.
   * @return Eine Liste an {count} Randomfoods (min. 1 - max. 10)
   */
  @GetMapping("{count}")
  public List<String> returnRandomFoods(@PathVariable int count) {
    if (count > 10 || count < 1) {

      // Fix eine kleine Exception aufspannen
      @ResponseStatus(HttpStatus.FORBIDDEN)
      class ForbiddenException extends RuntimeException {
      }

      // Direkt werfen
      throw new ForbiddenException();
    }

    return Stream.iterate(0, i -> i + 1)
        .limit(count)
        .map(i -> generateRandomfood())
        .collect(Collectors.toList());
  }

}
