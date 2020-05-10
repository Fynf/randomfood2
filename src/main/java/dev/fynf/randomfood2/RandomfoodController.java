package dev.fynf.randomfood2;

import dev.fynf.randomfood2.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RandomfoodController {
  private final FoodRepository foodRepository;
  private final ModifierRepository modifierRepository;
  private final ConnectorRepository connectorRepository;

  @Autowired
  public RandomfoodController(FoodRepository foodRepository,
                              ModifierRepository modifierRepository,
                              ConnectorRepository connectorRepository) {
    this.foodRepository = foodRepository;
    fillFoodRepository();

    this.modifierRepository = modifierRepository;
    fillFoodModifierList();

    this.connectorRepository = connectorRepository;
    fillConnectors();

  }

  private static int getRandomIndex(int listLength) {
    return ThreadLocalRandom.current().nextInt(0, listLength);
  }

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

  private String generateRandomfood() {
    List<Long> forbiddenFoodIndices = new LinkedList<>();
    List<Long> forbiddenModifierIndices = new LinkedList<>();
    List<Long> forbiddenConnectorIndices = new LinkedList<>();

    //Die SQl Abfrage funktioniert nicht mit einer leeren Liste (wtf)
    forbiddenFoodIndices.add(-1L);
    forbiddenModifierIndices.add(-1L);
    forbiddenConnectorIndices.add(-1L);
    StringBuilder sb = new StringBuilder();

    //Ein Essen haben wir mindestens
    sb.append(appendFood(forbiddenFoodIndices));

    // Mit optionalem Modifier
    if (ThreadLocalRandom.current().nextBoolean())
      sb.append(appendModifier(forbiddenModifierIndices));

    //Dazu 1-5 Extragerichte
    for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {

      // Führender Verbinder
      sb.append(appendConnector(forbiddenConnectorIndices));

      sb.append(appendFood(forbiddenFoodIndices));
      if (ThreadLocalRandom.current().nextBoolean())
        sb.append(appendModifier(forbiddenModifierIndices));
    }

    return sb.toString();
  }

  private String appendFood(List<Long> forbiddenFoodIndices) {
    // Frage von der Datenbank eine Liste aller Gerichte ab, ohne Gerichte,
    // deren DB-ID in der Sperr-Liste enthalten sind.
    List<Food> foodList = foodRepository.findAllByIdNotIn(forbiddenFoodIndices);

    // Aus der Liste von oben holen wir und von einer zufälligen Stelle ein Food.
    Food food = foodList.get(getRandomIndex(foodList.size()));

    // Die DB-ID des ausgewählten Foods wird in die Sperr-Liste übernommen
    forbiddenFoodIndices.add(food.getId());

    return food.getFoodName();
  }

  private String appendModifier(List<Long> forbiddenModifierIndices) {
    // Frage von der Datenbank eine Liste aller Modifier ab, ohne die,
    // deren DB-ID in der Sperr-Liste enthalten sind.
    List<Modifier> modifierList = modifierRepository.findAllByIdNotIn(forbiddenModifierIndices);

    // Aus der entstandenen Liste holen wir und von einer zufälligen Stelle einen Modifier.
    Modifier modifier = modifierList.get(getRandomIndex(modifierList.size()));

    // Die DB-ID des Modifiers, den wir ausgesucht haben, wird in die Sperrliste übernommen
    forbiddenModifierIndices.add(modifier.getId());

    return modifier.getModifierName();
  }

  private String appendConnector(List<Long> forbiddenConnectorIndices) {
    // DB Abfrage für alle Connectors ohne die Verbotenen
    List<Connector> connectorList = connectorRepository.findAllByIdNotIn(forbiddenConnectorIndices);

    // Zufällige Auswahl aus dem Ergebnis
    Connector connector = connectorList.get(getRandomIndex(connectorList.size()));

    //DB-ID wird gesperrt
    forbiddenConnectorIndices.add(connector.getId());

    return connector.getConnectorName();
  }

  private void fillFoodRepository() {

    List<String> foodNames = new LinkedList<>();
    try {
      Scanner sc = new Scanner(new File("food.txt"));
      while (sc.hasNextLine()) {
        foodNames.add(sc.nextLine());
      }
      sc.close();
    } catch (FileNotFoundException f) {
      Logger l = LoggerFactory.getLogger("");
      l.error("Dateifehler beim füllen des Food Repositorys");

    }
    foodNames.stream()
        .map(Food::new)
        .forEach(foodRepository::save);
  }

  private void fillFoodModifierList() {
    List<String> modifiers = new LinkedList<>();
    try {
      Scanner sc = new Scanner(new File("modifiers.txt"));
      while (sc.hasNextLine()) {
        modifiers.add(sc.nextLine());
      }
      sc.close();
    } catch (FileNotFoundException f) {
      Logger l = LoggerFactory.getLogger("");
      l.error("Dateifehler beim füllen des Modifier Repositorys");
    }
    modifiers.stream()
        .map(Modifier::new)
        .forEach(modifierRepository::save);
  }

  private void fillConnectors() {
    List<String> connectors = new LinkedList<>();
    try {
      Scanner sc = new Scanner(new File("connectors.txt"));
      while (sc.hasNextLine()) {
        connectors.add(sc.nextLine());
      }
      sc.close();
    } catch (FileNotFoundException f) {
      Logger l = LoggerFactory.getLogger("");
      l.error("Dateifehler beim füllen des Connector Repositorys");
    }
    connectors.stream()
        .map(Connector::new)
        .forEach(connectorRepository::save);
  }

}
