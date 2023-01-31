package dev.fynf.randomfood2.foodapi

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class WeirdAmountOfFoodException(message: String?) : RuntimeException(message)