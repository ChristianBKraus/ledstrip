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
import jupiterpa.ledstrip.model.Led; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


@RequestMapping(path = LEDStripController.PATH)
@RestController
public class LEDStripController {
    public static final String PATH ="/ledstrip";

    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Inject
	private LEDStripService service;
	
	@GetMapping("")
	public List<Led> ledstrip() {
        MDC.put("endpoint", "GET: " + PATH );
        logger.info(TECHNICAL, "HTTP GET /ledstrip");
        
		return service.getAll();
	}

	@GetMapping("/{row}/{column}")
	public Led ledstrip(@PathVariable int row, 
			            @PathVariable int column) {
        MDC.put("endpoint", "GET: " + PATH + "/" + row + "/" + column );
		logger.info(TECHNICAL, "HTTP GET /ledstrip/" +row+"/"+column);

		Led res = service.get(row,column);
		
		logger.info(TECHNICAL, "HTTP Result: "+res,res);
		return res;
	}


	@PutMapping("")
	public Led ledstrip(@RequestBody Led led){
        MDC.put("endpoint", "PUT: " + PATH );
		logger.info(TECHNICAL, "HTTP PUT /ledstrip " + led,led);

		Led res = service.update(led);
		
		logger.info(TECHNICAL, "HTTP Result: "+ res,res);
		return res;
	}
}
