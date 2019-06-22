package application;
	
import java.util.ArrayList;

import com.sun.corba.se.spi.orb.StringPair;

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
		launch(args);
		mockTest();
	}
	
	private static void mockTest() {
		
		State qoState = new State("qo",100.0,100.0);
		State q1State = new State("q1",100.0,50.0);
		State q2State = new State("q2",200.0,100.0);
		
		Transition oneTransition = new Transition("b", qoState, q2State);
		Transition twoTransition = new Transition("a", qoState, q1State);
		Transition threeTransition = new Transition("a", q1State, q1State);
		Transition fourTransition = new Transition("b", q1State, q2State);
		
		ArrayList<State> stateArray = new ArrayList<State>();
		stateArray.add(qoState);
		stateArray.add(q1State);
		stateArray.add(q2State);
		
		ArrayList<Transition> trasitionArray = new ArrayList<Transition>();
		trasitionArray.add(oneTransition);
		trasitionArray.add(twoTransition);
		trasitionArray.add(threeTransition);
		trasitionArray.add(fourTransition);
		
		ArrayList<State> acceptanceStatesArray = new ArrayList<State>();
		acceptanceStatesArray.add(q2State);
		
		String[] alphabet = {"a","b"};
		
		
		Automata a1 = new Automata(stateArray, alphabet, qoState, acceptanceStatesArray);;
		
		String word = "aab";
		
		System.out.println("The word " + word + " belongs to the automata: " + a1.recgonizeWord(word));
		
	}
}
