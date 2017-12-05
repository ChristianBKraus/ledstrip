package jupiterpa.ledstrip.model;

import org.springframework.data.annotation.Id;

public class Led {
	
	@Id
	public String id;
	
	int row;
	int column;		
	int red = 0;
	int green = 0;
	int blue = 0;

	public Led() {}
	
	public Led(int row, int column, int red, int green, int blue) {
		this.row = row;
		this.column = column;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Led(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void update(Led led) {
		red = led.red;
		green = led.green;
		blue = led.blue;
	}
	
	@Override
	public String toString() {
		return "LED " + row + "/" + column + ": " + red + "/" + green + "/" + blue;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

}
