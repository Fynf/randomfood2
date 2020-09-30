package dev.fynf.randomfood2.foodapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PathVariableDatatypeException extends RuntimeException {
  public PathVariableDatatypeException(String message) {
    super(message);
  }
}
