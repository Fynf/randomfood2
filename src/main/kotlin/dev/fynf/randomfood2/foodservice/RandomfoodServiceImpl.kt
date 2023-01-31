package dev.fynf.randomfood2.foodservice

import dev.fynf.randomfood2.entities.ConnectorRepository
import dev.fynf.randomfood2.entities.FoodRepository
import dev.fynf.randomfood2.entities.ModifierRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
class RandomfoodServiceImpl
/**
 * Setzt die DB-Verbindungen auf und stellt die Tabellen so der Programm-logik zur Verfügung.
 *
 * @param foodRepository      repräsentiert die food Tabelle aus der DB
 * @param modifierRepository  repräsentiert die modifier Tabelle aus der DB
 * @param connectorRepository repräsentiert die connector Tabelle aus der DB
 */ @Autowired constructor(
    private val foodRepository: FoodRepository,
    private val modifierRepository: ModifierRepository,
    private val connectorRepository: ConnectorRepository
) : RandomFoodService {
    override fun getFood(): String {
        return generateRandomfood()
    }

    override fun getMultipleFoods(amount: Int): List<String> {
        return Stream.iterate(0) { i: Int -> i + 1 }.limit(amount.toLong()).map { generateRandomfood() }
            .collect(Collectors.toList())
    }

    /**
     * Generiert ein zufälliges Gericht aus mehreren Textblöcken.
     * Ein Randomfood besteht aus 2 bis 6 Essen, die einen optionalen Modifikator besitzen und
     * mit einer Verbindungsfloskel verbunden werden.
     *
     * @return 1 Randomfood als String
     */
    private fun generateRandomfood(): String {
        val forbiddenFoodIndices: MutableList<Long> = LinkedList()
        val forbiddenModifierIndices: MutableList<Long> = LinkedList()
        val forbiddenConnectorIndices: MutableList<Long> = LinkedList()

        // Die SQl Abfrage funktioniert nicht mit einer leeren Liste (wtf)
        forbiddenFoodIndices.add(-1L)
        forbiddenModifierIndices.add(-1L)
        forbiddenConnectorIndices.add(-1L)
        val sb = StringBuilder()

        // Ein Essen haben wir mindestens
        sb.append(appendFood(forbiddenFoodIndices))

        // Mit optionalem Modifier
        if (ThreadLocalRandom.current().nextBoolean()) {
            sb.append(appendModifier(forbiddenModifierIndices))
        }

        // Dazu 1-5 Extragerichte
        for (i in 0 until ThreadLocalRandom.current().nextInt(1, 5)) {

            // Führender Verbinder
            sb.append(appendConnector(forbiddenConnectorIndices))

            // Erneut Essen mit optionalem Modifier
            sb.append(appendFood(forbiddenFoodIndices))
            if (ThreadLocalRandom.current().nextBoolean()) {
                sb.append(appendModifier(forbiddenModifierIndices))
            }
        }

        // Thank you String-Builder, very cool
        return sb.toString()
    }

    private fun appendFood(forbiddenFoodIndices: MutableList<Long>): String {
        // Frage von der Datenbank eine Liste aller Gerichte ab, ohne Gerichte,
        // deren DB-ID in der Sperr-Liste enthalten sind.
        val foodList = foodRepository.findAllByIdNotIn(forbiddenFoodIndices)

        // Aus der Liste von oben holen wir und von einer zufälligen Stelle ein Food.
        val food = foodList!![getRandomIndex(foodList.size)]

        // Die DB-ID des ausgewählten Foods wird in die Sperr-Liste übernommen
        forbiddenFoodIndices.add(food!!.id!!)
        return food.foodName
    }

    private fun appendModifier(forbiddenModifierIndices: MutableList<Long>): String {
        // Frage von der Datenbank eine Liste aller Modifier ab, ohne die,
        // deren DB-ID in der Sperr-Liste enthalten sind.
        val modifierList = modifierRepository.findAllByIdNotIn(forbiddenModifierIndices)

        // Aus der entstandenen Liste holen wir und von einer zufälligen Stelle einen Modifier.
        val modifier = modifierList?.get(getRandomIndex(modifierList.size))

        // Die DB-ID des Modifiers, den wir ausgesucht haben, wird in die Sperrliste übernommen
        forbiddenModifierIndices.add(modifier!!.id!!)
        return modifier.modifierName
    }

    private fun appendConnector(forbiddenConnectorIndices: MutableList<Long>): String {
        // DB Abfrage für alle Connectors ohne die Verbotenen
        val connectorList = connectorRepository.findAllByIdNotIn(forbiddenConnectorIndices)

        // Zufällige Auswahl aus dem Ergebnis
        val connector = connectorList!![getRandomIndex(
            connectorList.size
        )]

        // DB-ID wird gesperrt
        forbiddenConnectorIndices.add(connector!!.id!!)
        return connector.connectorName
    }


    private fun getRandomIndex(listLength: Int): Int {
        return ThreadLocalRandom.current().nextInt(0, listLength)
    }

}