package jupiterpa.ledstrip.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GPIOException extends RuntimeException{
	public GPIOException(String text) {
		super(text);
	}
}
