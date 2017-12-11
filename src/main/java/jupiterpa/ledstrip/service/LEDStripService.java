package jupiterpa.ledstrip.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.model.Led;
import jupiterpa.ledstrip.model.LedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LEDStripService {

	private static final Logger logger = LoggerFactory.getLogger(LEDStripService.class);
	
	List<Led> leds = new ArrayList<Led>();

	@Autowired GPIO gpio;
	@Autowired LedRepository repo;
	@Autowired LEDStripConfiguration configuration;

	public LEDStripService() { }

	public List<Led> getAll() {
		return leds;
	}

	public Led get(int row, int column) {
		return leds.get(_index(row, column));
	}

	public Led update(Led led) throws GPIOException {
		int index = _index(led.getRow(), led.getColumn());
		Led res = repo.findByRowAndColumn(led.getRow(), led.getColumn());
		res.update(led);
		repo.save(res);
		gpio.update(index, led,configuration.getPythonProgram());
		leds.get(index).update(led);
		return led;
	}

	public void initialize() {
		if (repo.findByRowAndColumn(0, 0) == null) {
			logger.info("Service No State found, initializing...");
			initializeLED();
			repo.save(leds);
			initializeGPIO();
		} else {
			logger.info("Service State stored, initializing...");
			leds = repo.findAll();
			initializeGPIO();
		}
		logger.info("Service State initialized");
	}
	
	
	private int _index(int row, int column) {
		int rows = configuration.getRows();
		if (column % 2 == 0) {
			return ( (column + 1) * rows ) - row - 1;
		} else 
			return row + column * rows;
	}

	private void initializeLED() {
		for (int i = 0; i < configuration.getRows(); i++) {
			for (int j = 0;j < configuration.getColumns(); j++) {
				leds.add(new Led(i,j));
			}
		}
	}
	private void initializeGPIO() {
		Iterator<Led> i = leds.iterator();
		while (i.hasNext()) {
			Led led = i.next();
			gpio.update(_index(led.getRow(),led.getColumn()),led, configuration.getPythonProgram());
		}
	}

}
