package dev.fynf.randomfood2;

import dev.fynf.randomfood2.foodservice.RandomFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RandomfoodWebController {

  @Autowired
  private RandomFoodService randomFoodService;

  /**
   * Holt sich ein (1) Randomfood vom Service und reicht es an Thymeleaf weiter,
   * um dem Nutzer die output.html mit einem eingefügten Randomfood anzuzeigen.
   *
   * @param m ist das Model von Spring, um Thymeleaf den Zugriff auf die Attribute zu ermöglichen
   * @return output, um Thymeleaf zu signalisieren, dass der Nutzer die output.html sehen soll
   */
  @GetMapping("/")
  public String returnRandomFood(Model m) {
    String[] appetizers = new String[]
        {"heute gibt es", "wir servieren heute", "die Empfehlung des Hauses ist", "koch' doch mal",
            "probier' doch mal", "der Chefkoch empfiehlt heute", "in der Kantine gibt's",
            "wie wär's mit", "die Mensa serviert", "was du da riechst ist"};

    m.addAttribute("appetizer",
        appetizers[ThreadLocalRandom.current().nextInt(0, appetizers.length - 1)]);
    m.addAttribute("randomfood", randomFoodService.getFood());

    return "output";
  }
}
