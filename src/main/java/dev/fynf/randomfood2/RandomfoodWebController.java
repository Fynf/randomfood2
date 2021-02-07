package dev.fynf.randomfood2;

import dev.fynf.randomfood2.entities.Appetizer;
import dev.fynf.randomfood2.entities.AppetizerRepository;
import dev.fynf.randomfood2.foodservice.RandomFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class RandomfoodWebController {

  @Autowired
  private RandomFoodService randomFoodService;
  @Autowired
  private AppetizerRepository appetizerRepository;

  /**
   * Holt sich ein (1) Randomfood vom Service und reicht es an Thymeleaf weiter,
   * um dem Nutzer die output.html mit einem eingefügten Randomfood anzuzeigen.
   *
   * @param m ist das Model von Spring, um Thymeleaf den Zugriff auf die Attribute zu ermöglichen
   * @return output, um Thymeleaf zu signalisieren, dass der Nutzer die output.html sehen soll
   */
  @GetMapping("/")
  public String returnRandomFood(Model m) {

    m.addAttribute("appetizer", getAppetizer());
    m.addAttribute("randomfood", randomFoodService.getFood());

    return "output";
  }


  private String getAppetizer() {
    List<Appetizer> appetizers = appetizerRepository.findAll();
    Collections.shuffle(appetizers);
    return appetizers.get(0).getAppetizerName();
  }
}
