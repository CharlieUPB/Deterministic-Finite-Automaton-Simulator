package application;

public class State {

	private String name;
	private Double xCoord;
	private Double yCoord;
	static int RADIUS = 25;
	
	public State(String name, Double xCoord, Double yCoord) 
	{
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getxCoord() {
		return xCoord;
	}

	public void setxCoord(Double xCoord) {
		this.xCoord = xCoord;
	}

	public Double getyCoord() {
		return yCoord;
	}

	public void setyCoord(Double yCoord) {
		this.yCoord = yCoord;
	}

}
