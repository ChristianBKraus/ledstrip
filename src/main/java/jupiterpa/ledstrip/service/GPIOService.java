package jupiterpa.ledstrip.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.model.Led;

@Component
@Profile("default")
public class GPIOService implements GPIO {

	@Autowired LEDStripConfiguration configuration;
	
	public void update(int index, Led led) throws GPIOException {
		
		String program = configuration.getPythonProgram();

		ProcessBuilder pb = new ProcessBuilder("python", program, Integer.toString(index),
				Integer.toString(led.getRed()), Integer.toString(led.getGreen()), Integer.toString(led.getBlue()));
		Process p;
		try {
			p = pb.start();
			p.waitFor();
			int exit = p.exitValue();
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
}
