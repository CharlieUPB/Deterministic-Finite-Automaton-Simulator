package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		mockTest();
		launch(args);
	}
	
	private static void mockTest() {
		
		State qoState = new State("qo",100.0,100.0);
		State q1State = new State("q1",100.0,50.0);
		State q2State = new State("q2",200.0,100.0);
		State q3State = new State("q3",200.0,100.0);
		
		Transition oneTransition = new Transition('1', qoState, q1State);
		Transition twoTransition = new Transition('0', qoState, q2State);
		Transition threeTransition = new Transition('0', q1State, q3State);
		Transition fourTransition = new Transition('1', q1State, qoState);
		Transition fiveTransition = new Transition('0', q2State, qoState);
		Transition sixTransition = new Transition('1', q2State, q3State);
		Transition SevenTransition = new Transition('0', q3State, q1State);
		Transition EigthTransition = new Transition('1', q3State, q2State);
		
		
		ArrayList<State> stateArray = new ArrayList<State>();
		stateArray.add(qoState);
		stateArray.add(q1State);
		stateArray.add(q2State);
		stateArray.add(q3State);
		
		ArrayList<Transition> trasitionArray = new ArrayList<Transition>();
		trasitionArray.add(oneTransition);
		trasitionArray.add(twoTransition);
		trasitionArray.add(threeTransition);
		trasitionArray.add(fourTransition);
		trasitionArray.add(fiveTransition);
		trasitionArray.add(sixTransition);
		trasitionArray.add(SevenTransition);
		trasitionArray.add(EigthTransition);
		
		
		ArrayList<State> acceptanceStatesArray = new ArrayList<State>();
		acceptanceStatesArray.add(qoState);		
		
		Automata a1 = new Automata(stateArray, qoState, acceptanceStatesArray, trasitionArray);
		
		String word = "11110000";
		
		System.out.println("The word " + word + " belongs to the automata: " + a1.recgonizeWord(word, 0, a1.getInitialState()));
		
	}
}
