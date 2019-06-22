package application;



import com.sun.javafx.css.Rule;
import com.sun.net.httpserver.Authenticator.Success;
import com.sun.prism.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;


import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class sceneAutomataController {

	@FXML
	private TabPane mainTab;

	@FXML 
	private Tab inicioTab;
	
	@FXML
	private Button createEditButton;

	@FXML
	private Button openFileButton;


	@FXML
	private MenuBar topMenu;   

	@FXML
	private Menu menuOpenFile;

	@FXML
	private Label projectName;


	@FXML
	private ListView recentFilesList;


	@FXML 
	private Tab automataTab;
	
	@FXML
	private AnchorPane drawAreaAnchorPane;
	
	@FXML
	private Button newStateButton;
	
	@FXML
	private Button newTransitionButton;
	
	@FXML
	private Button deleteButton;
	
	
	@FXML
	private TextField inputString;
        
    @FXML
    private Label labelCurrentInput;

    @FXML
	private Button btnStart;

	@FXML
	private AnchorPane inputCadenaEntrada;
	
	@FXML
	private AnchorPane pane;
	
	ToggleGroup toggleGroup = new ToggleGroup();

	FileManager fManager = new FileManager();
	
	private enum editionType
	{
		NEWSTATE, 
		NEWTRANSITION,
		DELETE;
	}
	
	editionType currentEditionType;
	
	int numClicksTransition = 0;

	public sceneAutomataController()
	{

	}


	@FXML
	private void initialize()
	{
		//this.loadRecentFiles();
		//this.labelCurrentInput.setTooltip(new Tooltip("Entrada"));
	}


	//Called when a file is double clicked from the main menu

	@FXML
	public void getFile(MouseEvent event) {
		if(event.getClickCount() == 2) {
			String fileSelected = this.recentFilesList.getSelectionModel().getSelectedItem().toString();
			Alert infoAlert = new Alert(AlertType.INFORMATION);
			infoAlert.setHeaderText("Loading your file");
			infoAlert.setContentText("Opening your saved file. Click on Ok to continue...");
			infoAlert.showAndWait();
			this.projectName.setText(fileSelected.toUpperCase());
			this.mainTab.getSelectionModel().selectNext();
			try {
				//this.automata = this.fm.getAutomata(fileSelected);
                //this.loadResources(this.automata);
			} catch (Exception ex) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Oops! There was a problem");
				errorAlert.setContentText("Error while retrieving the file.");
				errorAlert.showAndWait();
			}
		}
	}

	
	
	
	
	
	
	@FXML
	public void createAutomatonOnMouseClicked(MouseEvent event)
	{
		switch (this.currentEditionType) 
		{
			case NEWSTATE:
				double coordX = event.getX();
				double coordY = event.getY();
				System.out.println("Crear circulo.");
				System.out.println("X: " + event.getX());
				System.out.println("Y: " + event.getY());
				
				Circle circle = new Circle(coordX,coordY, 25, javafx.scene.paint.Color.BLUE);
				
				this.drawAreaAnchorPane.getChildren().add(circle);
			break;
			
			case NEWTRANSITION:
				if(event.getClickCount() == 2)
				{
					createNewTransition(event);
				}
			break;
				
			case DELETE:
				
			break;

			default:
			break;
		}
		
	}
	
	public void setEditionVariableState()
	{
		this.currentEditionType = editionType.NEWSTATE;
	}
	
	public void setEditionVariableTransition()
	{
		this.currentEditionType = editionType.NEWTRANSITION;
	}
	
	public void setEditionVariableDelete()
	{
		this.currentEditionType = editionType.DELETE;
	}
	
	

	public void createNewTransition(MouseEvent event) 
	{
		double xIni = 0;
		double yIni = 0;
		double xFinal = 0;
		double yFinal = 0;
		
		if(event.getClickCount() == 1)
		{
			xIni = event.getX();
			yIni = event.getY();
		}
		else if (event.getClickCount() == 2) 
		{
			xFinal = event.getX();
			yFinal = event.getY();
		}


	    CubicCurve curve1 = new CubicCurve(xIni, yIni, 125, 225, 325, 225, xFinal, yFinal);
	    curve1.setStroke(javafx.scene.paint.Color.BLACK);
	    curve1.setStrokeWidth(1);
	    curve1.setFill( null);

	    double size=Math.max(curve1.getBoundsInLocal().getWidth(),
	                         curve1.getBoundsInLocal().getHeight());
	    double scale=size/4d;

	    Point2D ori=eval(curve1,0);
	    Point2D tan=evalDt(curve1,0).normalize().multiply(scale);
	    Path arrowIni=new Path();
	    arrowIni.getElements().add(new MoveTo(ori.getX()+0.2*tan.getX()-0.2*tan.getY(),
	                                        ori.getY()+0.2*tan.getY()+0.2*tan.getX()));
	    arrowIni.getElements().add(new LineTo(ori.getX(), ori.getY()));
	    arrowIni.getElements().add(new LineTo(ori.getX()+0.2*tan.getX()+0.2*tan.getY(),
	                                        ori.getY()+0.2*tan.getY()-0.2*tan.getX()));

	    ori=eval(curve1,1);
	    tan=evalDt(curve1,1).normalize().multiply(scale);
	    Path arrowEnd=new Path();
	    arrowEnd.getElements().add(new MoveTo(ori.getX()-0.2*tan.getX()-0.2*tan.getY(),
	                                        ori.getY()-0.2*tan.getY()+0.2*tan.getX()));
	    arrowEnd.getElements().add(new LineTo(ori.getX(), ori.getY()));
	    arrowEnd.getElements().add(new LineTo(ori.getX()-0.2*tan.getX()+0.2*tan.getY(),
	                                        ori.getY()-0.2*tan.getY()-0.2*tan.getX()));
	    
	    this.drawAreaAnchorPane.getChildren().addAll(curve1, arrowIni, arrowEnd);
	    
	}

	/**
	 * Evaluate the cubic curve at a parameter 0<=t<=1, returns a Point2D
	 * @param c the CubicCurve 
	 * @param t param between 0 and 1
	 * @return a Point2D 
	 */
	private Point2D eval(CubicCurve c, float t){
	    Point2D p=new Point2D(Math.pow(1-t,3)*c.getStartX()+
	            3*t*Math.pow(1-t,2)*c.getControlX1()+
	            3*(1-t)*t*t*c.getControlX2()+
	            Math.pow(t, 3)*c.getEndX(),
	            Math.pow(1-t,3)*c.getStartY()+
	            3*t*Math.pow(1-t, 2)*c.getControlY1()+
	            3*(1-t)*t*t*c.getControlY2()+
	            Math.pow(t, 3)*c.getEndY());
	    return p;
	}

	/**
	 * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
	 * @param c the CubicCurve 
	 * @param t param between 0 and 1
	 * @return a Point2D 
	 */
	private Point2D evalDt(CubicCurve c, float t){
	    Point2D p=new Point2D(-3*Math.pow(1-t,2)*c.getStartX()+
	            3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlX1()+
	            3*((1-t)*2*t-t*t)*c.getControlX2()+
	            3*Math.pow(t, 2)*c.getEndX(),
	            -3*Math.pow(1-t,2)*c.getStartY()+
	            3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlY1()+
	            3*((1-t)*2*t-t*t)*c.getControlY2()+
	            3*Math.pow(t, 2)*c.getEndY());
	    return p;
	}
	
	
	

	
	
	
	

	//Called when openFile from menu is clicked

	@FXML
	public void loadFile() {
		RadioMenuItem selectedRadioButton = (RadioMenuItem) this.toggleGroup.getSelectedToggle();
		String filename = selectedRadioButton.getText();
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setHeaderText("Loading your file");
		infoAlert.setContentText("Opening your saved file. Click on Ok to continue...");
		infoAlert.showAndWait();
		try {
			//this.automata = this.fm.getAutomata(filename);
			this.projectName.setText(filename.toUpperCase());
		} catch (Exception ex) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Oops! There was a problem");
			errorAlert.setContentText("Error while retrieving the file.");
			errorAlert.showAndWait();
		}

	}
        
        @FXML 
        public void createNewFile() {
        	//this.automata = new Automata();
        	this.inputString.setText("");
        	this.mainTab.getSelectionModel().selectFirst();
        }

	@FXML
	public void saveFile() {
		TextInputDialog dialog = new TextInputDialog("myFile");
		dialog.setTitle("Filename");
		dialog.setHeaderText("To save your file we need a name.");
		dialog.setContentText("Please enter your fileName: ");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> {
			try {
				//this.fm.saveAutomata(name, automata);
				RadioMenuItem r1 = new RadioMenuItem(name + ".json");
				r1.setToggleGroup(this.toggleGroup);
				this.menuOpenFile.getItems().add(r1);
			} catch (Exception ex) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Error while saving file");
				errorAlert.setContentText("FileName not valid.");
				errorAlert.showAndWait();		
			}
		});

	}

	@FXML 
	public void reRender() {
		this.loadRecentFiles();
	}
        
        private void loadRecentFiles() {
        	this.recentFilesList.getItems().clear();
        	this.menuOpenFile.getItems().clear();
        	try {
        		Files.newDirectoryStream(Paths.get("./savedData"),path -> path.toString().endsWith(".json"))
        		.forEach(filePath -> { 
        			RadioMenuItem r1 = new RadioMenuItem(filePath.getFileName().toString());
        			r1.setToggleGroup(this.toggleGroup);
        			this.recentFilesList.getItems().add(filePath.getFileName());
        			this.menuOpenFile.getItems().add(r1);
        		});
        	} catch (IOException ex) {
        		Alert errorAlert = new Alert(AlertType.ERROR);
        		errorAlert.setHeaderText("No files found");
        		errorAlert.setContentText(ex.toString());
        		errorAlert.showAndWait();
		}
	}


	@FXML 
	public void simulate() {

		if(this.btnStart.getText().equals("Start")) {
			this.topMenu.setDisable(true);
			this.inicioTab.setDisable(true);
			this.inputString.setDisable(true);
			this.btnStart.getStyleClass().set(1, "danger");
			this.btnStart.setText("Stop");
              
                        // TEST WORD
                        //System.out.println(possible);
                        if(true) {
                             this.animate();
                        } else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("ERROR!");
                            alert.setHeaderText("NO SE ENCONTRO SOLUCION");
                            alert.setContentText("La cadena de entrada no pertenece al lenguaje");
                            alert.showAndWait();
                            this.topMenu.setDisable(false);
                            this.inicioTab.setDisable(false);
                            this.inputString.setDisable(false);
                            this.btnStart.getStyleClass().set(1, "success");
                            this.btnStart.setText("Start");
                        }
		} else {
			this.topMenu.setDisable(false);
			this.inicioTab.setDisable(false);
			this.inputString.setDisable(false);
			this.btnStart.getStyleClass().set(1, "success");
			this.btnStart.setText("Start");
		}
                
               
	}

        
        private void animate() {
           
        }
        
        @FXML
    	public void showEditComponent() {
    	}
        
        @FXML 
        public void showAbout() {
            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("About US!");
                            alert.setHeaderText("Look for helps in our repo");
                            alert.setContentText("Git Repository: https://github.com/Eli-C/Pushdown-Automata-Simulator \n Feel Free to Open Issues, or give recommendatios \n The Team: Elionor Cordova \n - Eun Kyu Choi \n - Carlos Gamboa" );
                            
                            Platform.runLater(alert::showAndWait);
        }


	public void ErrorAlertMessage()
	{
		Alert errorAlert = new Alert(AlertType.ERROR);

		errorAlert.setHeaderText("Input not valid");
		errorAlert.setContentText("This text area must contains the appropiate words.");
		errorAlert.showAndWait();		
	}

	public void successMessageAlert()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("SUCCESS!");
		alert.setHeaderText("Variable saved");
		alert.showAndWait();
	}

}
