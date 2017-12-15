package jupiterpa.ledstrip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.model.Led;

@Component
@Profile("mock")
public class GPIOMock implements GPIO {

	@Autowired LEDStripConfiguration configuration;
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void update(int index, Led led) throws GPIOException {
		
		String program = configuration.getPython_update();
		logger.info(TECHNICAL, "Program: {}", program);
	}
	public int number() {
		return 9;
	}
}
