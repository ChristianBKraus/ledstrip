package jupiterpa.ledstrip.service;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.model.LED;

@Component
public class GPIO {
	public void update(int index, LED led) {
		System.out.println(led.toString());
	}
}
