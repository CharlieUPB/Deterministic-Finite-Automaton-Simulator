package application;

import java.util.ArrayList;

public class Automata {

	private ArrayList<State> states;
	private String[] alphabet;
	private State initialState;
	private ArrayList<State> acceptanceStates;
	
	
	public Automata() {}
	
	public Automata(ArrayList<State> states, String[] alphabet, State initalState, ArrayList<State> acceptanceStates) {
		this.states = states;
		this.alphabet = alphabet;
		this.initialState = initalState;
		this.acceptanceStates = acceptanceStates;
	}
	
	public boolean recgonizeWord(String word) {
		return false;
	}
	
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
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

	public ArrayList<State> getAcceptanceStates() {
		return acceptanceStates;
	}

	public void setAcceptanceStates(ArrayList<State> acceptanceStates) {
		this.acceptanceStates = acceptanceStates;
	}

}
