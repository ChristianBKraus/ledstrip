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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = LEDStripController.PATH)
@RestController
public class LEDStripController {
	public static final String PATH ="/ledstrip";
	private static final Logger logger = LoggerFactory.getLogger(LEDStripController.class);
	
	
	@Inject
	private LEDStripService service;
	
	@GetMapping("")
	public List<LED> ledstrip() {
		System.out.println("Test");
		logger.info("HTTP GET /ledstrip");
		return service.getAll();
	}

	@GetMapping("/{row}/{column}")
	public LED ledstrip(@PathVariable int row, 
			            @PathVariable int column) {
		logger.info("HTTP GET /ledstrip/" +row+"/"+column);
		LED res = service.get(row,column);
		logger.info("HTTP Result: "+res,res);
		return res;
	}


	@PutMapping("")
	public LED ledstrip(@RequestBody LED led){
		logger.info("HTTP PUT /ledstrip " + led,led);
		LED res = service.update(led);
		logger.info("HTTP Result: "+ res,res);
		return res;
	}
}
