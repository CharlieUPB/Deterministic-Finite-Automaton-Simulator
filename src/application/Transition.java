package application;

public class Transition {

	private String symbol;
	private State initialState;
	private State finalState;
	
	public Transition() {}
	
	public Transition(String symbol, State initialState, State finalState) {
		this.symbol = symbol;
		this.initialState = initialState;
		this.finalState = finalState;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
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
