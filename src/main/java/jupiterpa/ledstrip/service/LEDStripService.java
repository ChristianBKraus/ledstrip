package jupiterpa.ledstrip.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.model.LED;
import jupiterpa.ledstrip.service.MongoDB;

@Component
public class LEDStripService {

	List<LED> leds = new ArrayList<LED>();

	@Autowired GPIO gpio;
	@Autowired MongoDB db;

	public LEDStripService() { }

	public List<LED> getAll() {
		return leds;
	}

	public LED get(int row, int column) {
		return leds.get(_index(row, column));
	}

	public LED update(LED led) {
		int index = _index(led.getRow(), led.getColumn());
		db.update(led);
		gpio.update(index, led);
		leds.get(index).update(led);
		return led;
	}

	public void initialize() {
		if (db.find(0, 0) == null) {
			initializeLED();
			writeLED();
		} else {
			readLED();
			initializeGPIO();
		}
	}
	
	
	private int _index(int row, int column) {
		return row + column * 3;
	}

	private void initializeLED() {
		leds.add(new LED(0, 0));
		leds.add(new LED(1, 0));
		leds.add(new LED(2, 0));

		leds.add(new LED(0, 1));
		leds.add(new LED(1, 1));
		leds.add(new LED(2, 1));

		leds.add(new LED(0, 2));
		leds.add(new LED(1, 2));
		leds.add(new LED(2, 2));
	}
	private void writeLED() {
		Iterator<LED> i = leds.iterator();
		while (i.hasNext()) {
			db.insert(i.next());
		}
	}
	private void readLED() {
		leds = db.findAll();
	}
	private void initializeGPIO() {
		Iterator<LED> i = leds.iterator();
		while (i.hasNext()) {
			LED led = i.next();
			gpio.update(_index(led.getRow(),led.getColumn()),led);
		}
	}

}
