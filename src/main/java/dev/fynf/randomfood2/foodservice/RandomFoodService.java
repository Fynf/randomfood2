package dev.fynf.randomfood2.foodservice;

import java.util.List;


public interface RandomFoodService {
  // Erzeugung eines einzelnen Essens
  String getFood();

  // Erzeugung mehrerer Essen
  List<String> getMultipleFoods(int amount);

}
