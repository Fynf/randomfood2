package dev.fynf.randomfood2.foodapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WeirdAmountOfFoodException extends RuntimeException {
  public WeirdAmountOfFoodException(String message) {
    super(message);
  }
}
