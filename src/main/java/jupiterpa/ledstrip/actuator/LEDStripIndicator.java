package jupiterpa.ledstrip.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.service.GPIO;

@Component
public class LEDStripIndicator implements HealthIndicator {
  
	@Autowired GPIO gpio;
	@Autowired LEDStripConfiguration configuration;
	
    @Override
    public Health health() {
    	int act = gpio.number();
    	int exp = configuration.getColumns() * configuration.getRows();
    	if (act == exp) {
    		return Health.up()
    				     .withDetail("actualNumberOfLED", act)
    				     .withDetail("expectedNumberofLED", exp)
    				     .build();
    	} else {
    		return Health.down()
				     .withDetail("actualNumberOfLED", act)
				     .withDetail("expectedNumberofLED", exp)
				     .build();
    	}
    }
}