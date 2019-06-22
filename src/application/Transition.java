package application;

public class Transition {

	private char symbol;
	private State initialState;
	private State nextState;
	
	public Transition(char symbol, State initialState, State finalState) 
	{
		this.symbol = symbol;
		this.initialState = initialState;
		this.nextState = finalState;
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

}
