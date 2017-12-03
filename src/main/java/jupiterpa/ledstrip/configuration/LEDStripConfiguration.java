package jupiterpa.ledstrip.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="ledstrip")
public class LEDStripConfiguration {
	private int rows;
	private int columns;
	private String python_program;
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public String getPython_program() {
		return python_program;
	}
	public void setPython_program(String python_program) {
		this.python_program = python_program;
	}
	
}
