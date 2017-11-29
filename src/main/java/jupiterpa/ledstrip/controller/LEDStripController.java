package jupiterpa.ledstrip.controller;

import javax.inject.Inject;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.ledstrip.service.LEDStripService;
import jupiterpa.ledstrip.model.LED; 

@RestController
public class LEDStripController {
	
	@Inject
	private LEDStripService service;
	
	@RequestMapping("/")
	public String greeting(){
		return "Hello World!!";
	}
	
	@GetMapping("/ledstrip")
	public List<LED> ledstrip() {
		return service.getAll();
	}

	@GetMapping("/ledstrip/{row}/{column}")
	public LED ledstrip(@PathVariable int row, 
			            @PathVariable int column) {
		LED led = service.get(row,column);
		return led;
	}


	@PutMapping("/ledstrip")
	public LED ledstrip(@RequestBody LED led){
		return service.update(led);
	}
}
