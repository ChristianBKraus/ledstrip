package jupiterpa.ledstrip.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class SystemConfiguration {
	private String processor_architecture;

	public boolean isProd() {
		return processor_architecture != "AMD64";
	}
	
	public String getProcessor_architecture() {
		return processor_architecture;
	}
	public void setProcessor_architecture(String processor_architecture) {
		this.processor_architecture = processor_architecture;
	}
	
	
}
