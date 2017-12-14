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
	private String python_update;
	private String python_check;
	
	@Autowired SystemConfiguration system;
	
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
	public String getPython_update() {
		return python_update;
	}
	public void setPython_update(String python_update) {
		this.python_update = python_update;
	}
	public String getPython_check() {
		return python_check;
	}
	public void setPython_check(String python_check) {
		this.python_check = python_check;
	}

}
