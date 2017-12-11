package jupiterpa.ledstrip.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ColorNotExistsException extends RuntimeException{
	public ColorNotExistsException(String color, int value) {
		super("Invalid Color " + value + " for " + color);
	}
}
