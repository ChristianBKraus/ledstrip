package jupiterpa.ledstrip;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jupiterpa.ledstrip.controller.LEDStripController;
import jupiterpa.ledstrip.model.Led;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LEDStripControllerTest { 
	final String PATH = LEDStripController.PATH; 

	@Autowired
    private MockMvc mockMvc;
    
    @Test
    public void getOneLED() throws Exception {
    	mockMvc.perform(get(PATH+"/1/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.row").value("1"))
        .andExpect(jsonPath("$.column").value("1"));
    }

    @Test
    public void getAllLEDs() throws Exception {
    	mockMvc.perform(get(PATH))
        	.andExpect(status().isOk())
        	.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        	.andExpect(jsonPath("$",hasSize(9)))
        	.andExpect(jsonPath("$[0].row").value("0"));
    }
    @Test
    public void updateLED() throws Exception {
    	Led led = new Led(0,0,10,11,12);
    	mockMvc.perform( put(PATH).content(toJson(led)).contentType(APPLICATION_JSON_UTF8) )
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.row").value("0"))
            .andExpect(jsonPath("$.column").value("0"))
            .andExpect(jsonPath("$.red").value("10"))
            .andExpect(jsonPath("$.green").value("11"))
            .andExpect(jsonPath("$.blue").value("12"));

    	Led led2 = new Led(0,0,0,0,0);
    	mockMvc.perform( put(PATH).content(toJson(led2)).contentType(APPLICATION_JSON_UTF8) )
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.row").value("0"))
            .andExpect(jsonPath("$.column").value("0"))
            .andExpect(jsonPath("$.red").value("0"))
            .andExpect(jsonPath("$.green").value("0"))
            .andExpect(jsonPath("$.blue").value("0"));
    }
    @Test
    public void integration() throws Exception {
    	// Update LED
    	Led led = new Led(1,1,10,11,12);
    	mockMvc.perform( put(PATH).content(toJson(led)).contentType(APPLICATION_JSON_UTF8) )
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.row").value("1"))
            .andExpect(jsonPath("$.column").value("1"))
            .andExpect(jsonPath("$.red").value("10"))
            .andExpect(jsonPath("$.green").value("11"))
            .andExpect(jsonPath("$.blue").value("12"));
    		
    	// Query LED
    	mockMvc.perform(get(PATH+"/1/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.row").value("1"))
        .andExpect(jsonPath("$.column").value("1"))
        .andExpect(jsonPath("$.red").value("10"))
        .andExpect(jsonPath("$.green").value("11"))
        .andExpect(jsonPath("$.blue").value("12"));
    }
    
    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
    
}