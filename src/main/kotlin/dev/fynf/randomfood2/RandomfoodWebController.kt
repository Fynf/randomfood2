package dev.fynf.randomfood2

import dev.fynf.randomfood2.entities.AppetizerRepository
import dev.fynf.randomfood2.foodservice.RandomFoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
class RandomfoodWebController {
    @Autowired
    private lateinit var randomFoodService: RandomFoodService

    @Autowired
    private lateinit var appetizerRepository: AppetizerRepository

    /**
     * Holt sich ein (1) Randomfood vom Service und reicht es an Thymeleaf weiter,
     * um dem Nutzer die output.html mit einem eingefügten Randomfood anzuzeigen.
     *
     * @param m ist das Model von Spring, um Thymeleaf den Zugriff auf die Attribute zu ermöglichen
     * @return output, um Thymeleaf zu signalisieren, dass der Nutzer die output.html sehen soll
     */
    @GetMapping("/")
    fun returnRandomFood(m: Model): String {
        m.addAttribute("appetizer", appetizer)
        m.addAttribute("randomfood", randomFoodService.getFood())
        return "output"
    }

    private val appetizer: String
        get() {
            val appetizers = appetizerRepository.findAll()
            appetizers.shuffle()
            return appetizers[0]!!.appetizerName
        }
}