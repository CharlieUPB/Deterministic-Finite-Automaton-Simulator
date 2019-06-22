package application;

public class Transition {

	private char symbol;
	private State initialState;
	private State finalState;
	
	public Transition() {}
	
	public Transition(char symbol, State initialState, State finalState) {
		this.symbol = symbol;
		this.initialState = initialState;
		this.finalState = finalState;
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

	public State getFinalState() {
		return finalState;
	}

	public void setFinalState(State finalState) {
		this.finalState = finalState;
	}

}
