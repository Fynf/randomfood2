package dev.fynf.randomfood2.foodservice

interface RandomFoodService {
    // Erzeugung eines einzelnen Essens
    fun getFood(): String

    // Erzeugung mehrerer Essen
    fun getMultipleFoods(amount: Int): List<String>
}