package jupiterpa.ledstrip;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jupiterpa.ledstrip.controller.LEDStripController;
import jupiterpa.ledstrip.model.LED;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LEDStripControllerTest { 
	final String PATH = LEDStripController.PATH; 

	@Autowired
    private MockMvc mockMvc;
    
    @Test
    public void getOneLED() throws Exception {
    	LED led = new LED(1,1,10,10,10);
    	mockMvc.perform(get(PATH+"/1/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(content().string(containsString(toJson(led))));
    }

    @Test
    public void getAllLEDs() throws Exception {
    	LED led = new LED(1,1,10,10,10);
    	mockMvc.perform(get(PATH+"/1/1"))
        	.andExpect(status().isOk())
        	.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        	.andExpect(content().string(containsString(toJson(led))));
    }
    @Test
    public void updateLED() throws Exception {
    	LED led = new LED(0,0,10,10,10);
    	mockMvc.perform( put(PATH).content(toJson(led)).contentType(APPLICATION_JSON_UTF8) )
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
    		.andExpect(content().string(containsString(toJson(led))));

    	LED led2 = new LED(0,0,0,0,0);
    	mockMvc.perform( put(PATH).content(toJson(led2)).contentType(APPLICATION_JSON_UTF8) )
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
    		.andExpect(content().string(containsString(toJson(led2))));
    }
    
    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
    
}