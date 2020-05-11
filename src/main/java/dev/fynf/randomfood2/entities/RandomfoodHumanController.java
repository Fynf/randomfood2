package dev.fynf.randomfood2.entities;

import dev.fynf.randomfood2.RandomfoodGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RandomfoodHumanController extends RandomfoodGenerator {

  @Autowired
  public RandomfoodHumanController(FoodRepository foodRepository,
                                   ModifierRepository modifierRepository,
                                   ConnectorRepository connectorRepository) {
    super(foodRepository, modifierRepository, connectorRepository);
  }

  /**
   * Generiert ein (1) Randomfood und reicht es an Thymeleaf weiter, um dem Nutzer die huebsch.html
   * mit einem eingefügten Randomfood anzuzeigen.
   *
   * @param m ist das Model von Spring, um Thymeleaf den Zugriff auf die Attribute zu ermöglichen
   * @return huebsch, um Thymeleaf zu signalisieren, dass dem Nutzer die huebsch.html sehen soll
   */
  @GetMapping("/")
  public String returnRandomFood(Model m) {
    String[] appetizers = new String[]
        {"heute gibt es", "wir servieren heute", "die Empfehlung des Hauses ist", "koch' doch mal",
            "probier' doch mal", "der Chefkoch empfiehlt heute", "in der Kantine gibt's",
            "wie wär's mit"};

    m.addAttribute("appetizer",
        appetizers[ThreadLocalRandom.current().nextInt(0, appetizers.length - 1)]);
    m.addAttribute("randomfood", generateRandomfood());

    return "huebsch";
  }
}
