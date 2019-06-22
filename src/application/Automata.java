package application;

import java.util.ArrayList;

public class Automata {

	private ArrayList<State> states;
	private State initialState;
	private ArrayList<State> acceptanceStates;
	private ArrayList<Transition> transitions;
	
	public Automata(ArrayList<State> states, State initalState, ArrayList<State> acceptanceStates, ArrayList<Transition> transitions) 
	{
		this.states = states;
		this.initialState = initalState;
		this.acceptanceStates = acceptanceStates;
		this.transitions = transitions;
	}
	
	public boolean recgonizeWord(String word, int index, State currentState) 
	{
		
		// Cadena de entrada termino?
		if (index == word.length())
		{
			for (State acceptanceState : acceptanceStates) 
			{
				// Estamos en un estado de aceptacion?
				if (currentState == acceptanceState) 
				{
					System.out.println("Cadena pertenece al lenguaje generado por el automata");
					return true;
				} 
				else 
				{
					System.out.println("Cadena NO pertenece al lenguaje generado por el automata");
					return false;
				}
			}
		}
		else 
		{
			char currentSymbol = word.charAt(index);
			State nextState = this.getNextStateByTransition(currentState, currentSymbol);
			// Existe un estado siguiente con dicha transicion?
			if (nextState != null)
			{
				System.out.println("Realizando transicion de Estado: " + currentState.getName() + " con simbolo: " + currentSymbol + " HACIA Estado: " + nextState.getName());
				return this.recgonizeWord(word, index + 1, nextState);
			} 
			else 
			{
				System.out.println("Cadena NO pertenece al lenguaje generado por el automata");
				return false;
			}
		}
		return false;
	}
	
	// Obtiene el estado resultante de una transicion, dado un estado y un simbolo.
	// Implementacion de la funcion de transicion.
	private State getNextStateByTransition(State currentState, char symbol) 
	{
		for (Transition transition : this.transitions) 
		{
			if (transition.getInitialState() == currentState && transition.getSymbol() == symbol)
			{
				return transition.getNextState();
			}
		}
		
		// Si no existe una transicion retornamos null.
		return null;
	}
	
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
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
