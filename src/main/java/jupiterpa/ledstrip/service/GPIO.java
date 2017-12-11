package jupiterpa.ledstrip.service;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jupiterpa.ledstrip.model.Led;

@Component
public class GPIO {
	
	private static final Logger logger = LoggerFactory.getLogger(GPIO.class);
	@Autowired
	private CounterService counterService;
	
	public void update(int index, Led led, String program) {
		logger.info("GPIO Write: " + led + " [" + program + "]",led);
		counterService.increment("counter.led.update");

		// Sun's ProcessBuilder and Process example
		ProcessBuilder pb = 
				new ProcessBuilder("python", program,
						Integer.toString(index), 
						Integer.toString(led.getRed()),
						Integer.toString(led.getGreen()),
						Integer.toString(led.getBlue())
						);
		Map<String, String> env = pb.environment();
		try {
			Process p = pb.start();
			p.waitFor();
			int exit = p.exitValue();
			if (exit != 0) {
				logger.error("GPIO Write: Python program exited with " + Integer.toString(exit) );
				counterService.increment("counter.led.fail");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("GPIO Write: " + e);
			counterService.increment("counter.led.fail");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("GPIO Write: " + e);
			counterService.increment("counter.led.fail");
		}
	}
}
