package jupiterpa.ledstrip.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.model.LED;
import jupiterpa.ledstrip.service.MongoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LEDStripService {

	private static final Logger logger = LoggerFactory.getLogger(LEDStripService.class);
	
	List<LED> leds = new ArrayList<LED>();

	@Autowired GPIO gpio;
	@Autowired MongoDB db;
	@Autowired LEDStripConfiguration configuration;

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
		gpio.update(index, led,configuration.getPythonProgram());
		leds.get(index).update(led);
		return led;
	}

	public void initialize() {
		if (db.find(0, 0) == null) {
			logger.info("Service No State found, initializing...");
			initializeLED();
			writeLED();
			initializeGPIO();
		} else {
			logger.info("Service State stored, initializing...");
			readLED();
			initializeGPIO();
		}
		logger.info("Service State initialized");
	}
	
	
	private int _index(int row, int column) {
		return row + column * configuration.getRows();
	}

	private void initializeLED() {
		for (int i = 0; i < configuration.getRows(); i++) {
			for (int j = 0;j < configuration.getColumns(); j++) {
				leds.add(new LED(i,j));
			}
		}
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
			gpio.update(_index(led.getRow(),led.getColumn()),led, configuration.getPythonProgram());
		}
	}

}
