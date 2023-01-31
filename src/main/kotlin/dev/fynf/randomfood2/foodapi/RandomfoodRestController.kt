package dev.fynf.randomfood2.foodapi

import dev.fynf.randomfood2.foodservice.RandomFoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class RandomfoodRestController {
    @Autowired
    private lateinit var randomFoodService: RandomFoodService

    /**
     * Generiert eine Liste von {count} randomfoods und gibt diese als HTML-Response zurück.
     *
     *
     * Die Liste ist in ihrer Größe auf maximal 10 beschränkt,
     * höhere Anfragen und Zahlen < 1 werden mit FORBIDDEN geahndet.
     * Sollte die @PathVariable nicht als Integer vorliegen, weil z.B. jemand "Zehn" statt 10
     * geschrieben hat, wird die daraus entstehende NumberFormatException
     * gefangen und mit BAD_REQUEST bestraft.
     *
     * @param count Die vom Nutzer in der URL angegebene, gewünschte Anzahl von Randomfoods.
     * @return Eine Liste an {count} Randomfoods (min. 1 - max. 10)
     */
    @GetMapping("{count}")
    fun returnRandomFoods(@PathVariable count: Int): List<String> {
        try {
            if (count in 1..10) {
                return randomFoodService.getMultipleFoods(count)
            }
            // Wenn count keine Zahl ist, wirft Java eine NumberFormatException.
            // Die muss der Aufrufer aber nicht sehen, deshalb werfen wir unsere eigene.
        } catch (e: NumberFormatException) {
            throw PathVariableDatatypeException("The path-variable must be an integer, my dude.")
        }
        throw WeirdAmountOfFoodException("The parameter must be between one (1) and ten (10)!")
    }
}