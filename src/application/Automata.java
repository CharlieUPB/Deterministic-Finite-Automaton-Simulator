package application;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.java_cup.internal.runtime.Symbol;

public class Automata {

	private ArrayList<State> states;
	private String alphabet;
	private State initialState;
	private ArrayList<State> acceptanceStates;
	private ArrayList<Transition> transitions;

	public Automata() {}
	
	public Automata(ArrayList<State> states, String alphabet, State initalState, ArrayList<State> acceptanceStates, ArrayList<Transition> transitions) {
		this.states = states;
		this.alphabet = alphabet;
		this.initialState = initalState;
		this.acceptanceStates = acceptanceStates;
		this.transitions = transitions;
	}
	
	public boolean recgonizeWord(String word, int index) {
		char[] splitedSymbols = getSplitedSymbols(word);
		char initialSymbol = word.charAt(index);
		State currentState = this.initialState;
		if (this.getStateTroughTransition(currentState, initialSymbol) != null) {
			return this.recgonizeWord(word, index + 1);
		}
		
		return false;
	}
	
	// Obtiene el estado resultante de una transicion, dado un estado y un simbolo.
	// Implementacion de la funcion de transicion.
	private State getStateTroughTransition(State fromState, char symbol) {
		String fromStateName = fromState.getName();
		for(int i=0; i < this.transitions.size(); i++) {
			String transitionStateName = this.transitions.get(i).getInitialState().getName();

			// Si Existe una transicion
			if(fromStateName.equals(transitionStateName)) 
			{
				// Devolvemos el estado resultante de esa transicion
				return transitions.get(i).getFinalState();
			}
		}
		
		// Si no existe un transicion retornamos null.
		return null;
	}
	
	private char[] getSplitedSymbols(String word) {
		return word.toCharArray();
		
	}
	
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
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
	
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

}
