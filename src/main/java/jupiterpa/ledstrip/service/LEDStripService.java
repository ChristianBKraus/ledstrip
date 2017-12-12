package jupiterpa.ledstrip.service;

import java.util.ArrayList;
import java.util.Collection;
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
	
	@Autowired GPIO gpio;
	@Autowired LedRepository repo;
	@Autowired LEDStripConfiguration configuration;
 
	public LEDStripService() { }

	public List<Led> getAll() {
		return repo.findAll();
	}

	public Led get(int row, int column) {
		return repo.findByRowAndColumn(row, column);
	}

	public Led update(Led led) throws GPIOException {
		int index = _index(led.getRow(), led.getColumn());
		Led res = repo.findByRowAndColumn(led.getRow(), led.getColumn());
		res.update(led);
		gpio.update(index, led);
		return repo.save(res);
	}

	public void initialize() {
		if (repo.findByRowAndColumn(0, 0) == null) {
			logger.info("Service No State found, initializing...");
			Collection<Led> leds = initializeLED();
			repo.save(leds);
			initializeGPIO(leds);
		} else {
			logger.info("Service State stored, initializing...");
			Collection<Led> leds = repo.findAll();
			initializeGPIO(leds);
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

	private Collection<Led> initializeLED() {
		Collection<Led> leds = new ArrayList<Led>();
		for (int i = 0; i < configuration.getRows(); i++) {
			for (int j = 0;j < configuration.getColumns(); j++) {
				leds.add(new Led(i,j));
			}
		}
		return leds;
	}
	private void initializeGPIO(Collection<Led> leds) {
		Iterator<Led> i = leds.iterator();
		while (i.hasNext()) {
			Led led = i.next();
			gpio.update(_index(led.getRow(),led.getColumn()),led);
		}
	}

}
