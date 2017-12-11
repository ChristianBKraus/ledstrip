package jupiterpa.ledstrip.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LEDNotExistsException extends RuntimeException{
	public LEDNotExistsException(int row, int column) {
		super("LED " + row + "/" + column + " does not exist" );
	}
}
