package application;

import java.util.ArrayList;

public class Automata {

	private ArrayList<State> states;
	private State initialState;
	private ArrayList<State> acceptanceStates;
	private ArrayList<Transition> transitions;
	
	public Automata() {}
	
	public Automata(ArrayList<State> states, State initalState, ArrayList<State> acceptanceStates, ArrayList<Transition> transitions) 
	{
		this.states = states;
		this.initialState = initalState;
		this.acceptanceStates = acceptanceStates;
		this.transitions = transitions;
	}
	
	public boolean stateNameExists(String stateName)
	{
		for (State state : this.states) {
			if(state.getName().equals(stateName))
			{
				System.out.println("You can't add duplicate names on states");
				return true;
			}
		}
		return false;
	}
	
	public boolean duplicateTransition(char symbol, String initialStateName)
	{
		for (Transition transition : this.transitions) {
			if (transition.getInitialState().getName().equals(initialStateName))
			{
				if (transition.getSymbol() == symbol)
				{
					System.out.println("You cant have the same symbol with different transitions from one state in AFD");
					return true;
				}
			}
		}
		return false;
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
	
	public boolean DoesStateCollision(double xCoord, double yCoord)
	{
		double x1 = xCoord;
		double y1 = yCoord;
		
		for (State state : this.states) {
			
			double xDif = x1 - state.getxCoord();
			double yDif = y1 - state.getyCoord();
			double distanceSquared = xDif * xDif + yDif * yDif;
			boolean collision = distanceSquared < (State.RADIUS + State.RADIUS) * (State.RADIUS + State.RADIUS);
			if (collision)
			{
				return true;
			}
		}
		return false;
	}
	
	public State getStateByCoords(double xCoord, double yCoord) 
	{
		double x1 = xCoord;
		double y1 = yCoord;
		
		for (State state : this.states) 
		{
			double x0 = state.getxCoord();
			double y0 = state.getyCoord();
			boolean pointIsInside = (Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0)) ) <= ( State.RADIUS );
			
			if (pointIsInside)
			{
				System.out.println("Found the corresponding state");
				return state;
			}
		}
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
