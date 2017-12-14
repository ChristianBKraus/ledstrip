package jupiterpa.ledstrip.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import jupiterpa.ledstrip.service.GPIO;

@Component
public class HealthCheck implements HealthIndicator {
  
	@Autowired GPIO gpio;
	
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
              .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }
     
    public int check() {
        // Your logic to check health
        return 0;
    }
}