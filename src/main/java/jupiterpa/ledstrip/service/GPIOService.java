package jupiterpa.ledstrip.service;

import java.io.IOException;

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
@Profile("default")
public class GPIOService implements GPIO {

	@Autowired LEDStripConfiguration configuration;
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void update(int index, Led led) throws GPIOException {
		
		String program = configuration.getPython_update();
		int number = configuration.getColumns() * configuration.getRows();

		logger.info(TECHNICAL, "    GPIO program " + program);
		ProcessBuilder pb = new ProcessBuilder("python", program, Integer.toString(index), Integer.toString(number),
				Integer.toString(led.getRed()), Integer.toString(led.getGreen()), Integer.toString(led.getBlue()));
		Process p;
		try {
			p = pb.start();
			p.waitFor();
			int exit = p.exitValue();
			logger.info(TECHNICAL, "    GPIO exit code " + Integer.toString(exit));
			if (exit != 0) {
				throw new GPIOException("GPIO Write: Python program exited with " + Integer.toString(exit));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new GPIOException(e.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new GPIOException(e.toString());
		}
	}
	
	public int number() {
		String program = configuration.getPython_check();
		int number = configuration.getColumns() * configuration.getRows();

		logger.info(TECHNICAL, "    GPIO program " + program);
		ProcessBuilder pb = new ProcessBuilder("python", program, Integer.toString(number) );
		Process p;
		try {
			p = pb.start();
			p.waitFor();
			int exit = p.exitValue();
			logger.info(TECHNICAL, "    GPIO exit code " + Integer.toString(exit));
			if (exit <= 100 || exit > 150) 
				return 0;
			else
				return exit - 100;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
