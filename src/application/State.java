package application;

public class State {

	private String name;
	private Double xCoord;
	private Double yCoord;
	
	public State() {}
	
	public State(String name, Double xCoord, Double yCoord) {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (xCoord == null) {
			if (other.xCoord != null)
				return false;
		} else if (!xCoord.equals(other.xCoord))
			return false;
		if (yCoord == null) {
			if (other.yCoord != null)
				return false;
		} else if (!yCoord.equals(other.yCoord))
			return false;
		return true;
	}

}
