package jupiterpa.ledstrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jupiterpa.ledstrip.service.*;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired LEDStripService service;
	@Autowired MongoDB db;
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		service.initialize();
	}

}
