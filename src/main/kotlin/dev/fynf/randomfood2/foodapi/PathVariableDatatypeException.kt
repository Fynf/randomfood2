package dev.fynf.randomfood2.foodapi

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class PathVariableDatatypeException(message: String?) : RuntimeException(message)