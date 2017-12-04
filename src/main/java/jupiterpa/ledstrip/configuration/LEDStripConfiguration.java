package jupiterpa.ledstrip.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="ledstrip")
public class LEDStripConfiguration {
	private int rows;
	private int columns;
	private String python_program_test;
	private String python_program_prod;
	
	@Autowired SystemConfiguration system;
	
	public String getPythonProgram() {
		if (system.isProd()) 
			return getPython_program_test();
		else
			return getPython_program_prod();
	}
	
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

	public String getPython_program_test() {
		return python_program_test;
	}

	public void setPython_program_test(String python_program_test) {
		this.python_program_test = python_program_test;
	}

	public String getPython_program_prod() {
		return python_program_prod;
	}

	public void setPython_program_prod(String python_program_prod) {
		this.python_program_prod = python_program_prod;
	}	
	
}
