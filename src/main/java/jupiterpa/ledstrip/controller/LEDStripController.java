package jupiterpa.ledstrip.controller;

import javax.inject.Inject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.ledstrip.service.LEDStripService;
import jupiterpa.ledstrip.configuration.LEDStripConfiguration;
import jupiterpa.ledstrip.model.Led;


@RequestMapping(path = LEDStripController.PATH)
@RestController
public class LEDStripController {
    public static final String PATH ="/ledstrip";

	@Inject    private LEDStripService service;
	@Autowired LEDStripConfiguration configuration;
	
	@GetMapping("")
	public List<Led> getLedStrips() {
		return service.getAll();
	}

	@GetMapping("/{row}/{column}")
	public Led getLedStrip(@PathVariable int row, 
			            @PathVariable int column) {
		validatePosition(row,column);
		return service.get(row,column);
	}

	@PutMapping("")
	public Led putLedStrip(@RequestBody Led led){
		validatePosition(led.getRow(),led.getColumn());
		validateColor(led.getRed(), led.getGreen(), led.getBlue());
		return service.update(led);
	}
	
	private void validatePosition(int row, int column) throws LEDNotExistsException {
		if ( row < 0 || row >= configuration.getRows() || 
		     column < 0 || column >= configuration.getColumns() ) {
			throw new LEDNotExistsException(row,column);
		}
	}
	private void validateColor(int red, int green, int blue) throws ColorNotExistsException {
		if (red < 0 || red > 255 ) throw new ColorNotExistsException("red",red);
		if (green < 0 || green > 255 ) throw new ColorNotExistsException("green",red);
		if (blue < 0 || blue > 255 ) throw new ColorNotExistsException("blue",red);
	}
}
