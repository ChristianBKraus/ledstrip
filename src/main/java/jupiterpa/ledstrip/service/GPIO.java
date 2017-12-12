package jupiterpa.ledstrip.service;

import jupiterpa.ledstrip.model.Led;

public interface GPIO {
	public void update(int index, Led led) throws GPIOException;
}