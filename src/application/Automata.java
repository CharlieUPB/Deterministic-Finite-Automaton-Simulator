package application;

public class Automata {

	private State[] states;
	private String[] alphabet;
	private State initialState;
	private State[] acceptanceStates;
	
	
	public Automata() {}
	
	public Automata(State[] states, String[] alphabet, State initalState, State[] acceptanceStates) {
		this.states = states;
		this.alphabet = alphabet;
		this.initialState = initalState;
		this.acceptanceStates = acceptanceStates;
	}
	
	public boolean recgonizeWord(String word) {
		return false;
	}
	
	public State[] getStates() {
		return states;
	}

	public void setStates(State[] states) {
		this.states = states;
	}

	public String[] getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String[] alphabet) {
		this.alphabet = alphabet;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public State[] getAcceptanceStates() {
		return acceptanceStates;
	}

	public void setAcceptanceStates(State[] acceptanceStates) {
		this.acceptanceStates = acceptanceStates;
	}

}
