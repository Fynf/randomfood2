package dev.fynf.randomfood2.foodservice;

import dev.fynf.randomfood2.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RandomfoodServiceImpl implements RandomFoodService {
  private final FoodRepository foodRepository;
  private final ModifierRepository modifierRepository;
  private final ConnectorRepository connectorRepository;

  /**
   * Setzt die DB-Verbindungen auf und stellt die Tabellen so der Programm-logik zur Verfügung.
   *
   * @param foodRepository      repräsentiert die food Tabelle aus der DB
   * @param modifierRepository  repräsentiert die modifier Tabelle aus der DB
   * @param connectorRepository repräsentiert die connector Tabelle aus der DB
   */
  @Autowired
  public RandomfoodServiceImpl(FoodRepository foodRepository,
                               ModifierRepository modifierRepository,
                               ConnectorRepository connectorRepository) {

    this.foodRepository = foodRepository;
    this.modifierRepository = modifierRepository;
    this.connectorRepository = connectorRepository;
  }

  private static int getRandomIndex(int listLength) {
    return ThreadLocalRandom.current().nextInt(0, listLength);
  }

  @Override
  public String getFood() {
    return generateRandomfood();
  }

  @Override
  public List<String> getMultipleFoods(int amount) {
    return Stream.iterate(0, i -> i + 1)
        .limit(amount)
        .map(i -> generateRandomfood())
        .collect(Collectors.toList());
  }

  /**
   * Generiert ein zufälliges Gericht aus mehreren Textblöcken.
   * Ein Randomfood besteht aus 2 bis 6 Essen, die einen optionalen Modifikator besitzen und
   * mit einer Verbindungsfloskel verbunden werden.
   *
   * @return 1 Randomfood als String
   */
  private String generateRandomfood() {
    List<Long> forbiddenFoodIndices = new LinkedList<>();
    List<Long> forbiddenModifierIndices = new LinkedList<>();
    List<Long> forbiddenConnectorIndices = new LinkedList<>();

    // Die SQl Abfrage funktioniert nicht mit einer leeren Liste (wtf)
    forbiddenFoodIndices.add(-1L);
    forbiddenModifierIndices.add(-1L);
    forbiddenConnectorIndices.add(-1L);
    StringBuilder sb = new StringBuilder();

    // Ein Essen haben wir mindestens
    sb.append(appendFood(forbiddenFoodIndices));

    // Mit optionalem Modifier
    if (ThreadLocalRandom.current().nextBoolean()) {
      sb.append(appendModifier(forbiddenModifierIndices));
    }

    // Dazu 1-5 Extragerichte
    for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {

      // Führender Verbinder
      sb.append(appendConnector(forbiddenConnectorIndices));

      // Erneut Essen mit optionalem Modifier
      sb.append(appendFood(forbiddenFoodIndices));
      if (ThreadLocalRandom.current().nextBoolean()) {
        sb.append(appendModifier(forbiddenModifierIndices));
      }
    }

    // Thank you String-Builder, very cool
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

    // DB-ID wird gesperrt
    forbiddenConnectorIndices.add(connector.getId());

    return connector.getConnectorName();
  }


}
