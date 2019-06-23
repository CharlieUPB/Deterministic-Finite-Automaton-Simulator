package application;

public class Transition {

	private char symbol;
	private State initialState;
	private State nextState;
	private double x0Coord;
	private double y0Coord;
	private double x1Coord;
	private double y1Coord;
	
	
	public Transition(char symbol, State initialState, State finalState, double x0Coord, double y0Coord, double x1Coord, double y1Coord) 
	{
		this.symbol = symbol;
		this.initialState = initialState;
		this.nextState = finalState;
		this.x0Coord = x0Coord;
		this.y0Coord = y0Coord;
		this.x1Coord = x1Coord;
		this.y1Coord = y1Coord;
		
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public double getX0Coord() {
		return x0Coord;
	}

	public void setX0Coord(double x0Coord) {
		this.x0Coord = x0Coord;
	}

	public double getY0Coord() {
		return y0Coord;
	}

	public void setY0Coord(double y0Coord) {
		this.y0Coord = y0Coord;
	}

	public double getX1Coord() {
		return x1Coord;
	}

	public void setX1Coord(double x1Coord) {
		this.x1Coord = x1Coord;
	}

	public double getY1Coord() {
		return y1Coord;
	}

	public void setY1Coord(double y1Coord) {
		this.y1Coord = y1Coord;
	}


}
