package jupiterpa.ledstrip.service;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.model.LED;

@Component
public class GPIO {
	public void update(int index, LED led) {
		System.out.println(led.toString());

		// Sun's ProcessBuilder and Process example
		ProcessBuilder pb = 
				new ProcessBuilder("python", "ledstrip.py",
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
				System.out.println("Python program exited with " + Integer.toString(exit) );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
