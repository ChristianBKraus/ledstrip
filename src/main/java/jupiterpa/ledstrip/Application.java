package jupiterpa.ledstrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jupiterpa.ledstrip.model.Led;
import jupiterpa.ledstrip.model.LedRepository;
import jupiterpa.ledstrip.service.*;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired LEDStripService service;
	@Autowired MongoDB db;
	@Autowired LedRepository repo;
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		service.initialize();
		initializeRepo();
	}
	
	private void initializeRepo() {
		System.out.println("Repo Start");
		repo.deleteAll();
		repo.save(new Led(0,0));
		repo.save(new Led(1,0));
		for (Led led : repo.findAll()) {
			System.out.println(led);
		}
		System.out.println("Repo end");
	}

}
