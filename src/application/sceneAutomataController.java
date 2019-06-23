package application;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;


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
	private Tab crearAutomataTab;
	
	@FXML 
	private Tab automataTab;

	@FXML
	private AnchorPane drawAreaAnchorPane;
	
	@FXML
	private AnchorPane showAreaAnchorPane;

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
	private AnchorPane pane;

	private Automata automata = new Automata();

	ToggleGroup toggleGroup = new ToggleGroup();

	FileManager fManager = new FileManager();

	State initialState;
	State nextState;

	double firstClickedX = 0;
	double firstClickedY = 0;
	double secondClickedX = 0;
	double secondClickedY = 0;

	char transitionSymbol;
	String stateName;

	private enum editionType
	{
		NEWSTATE, 
		NEWTRANSITION,
		DELETE;
	}

	editionType currentEditionType;

	int numberOfTransitionClicks = 0;

	
	public sceneAutomataController() {}

	@FXML
	private void initialize()
	{
		this.loadRecentFiles();
		//this.labelCurrentInput.setTooltip(new Tooltip("Entrada"));
		this.crearAutomataTab.setDisable(true);
		this.automataTab.setDisable(true);
	}
	
	@FXML
	public void onSelectionChangedTab()
	{
		int indexOfTab = this.mainTab.getSelectionModel().getSelectedIndex();;
		System.out.println("index: " + indexOfTab);
		switch (indexOfTab) {
			case 0:
				this.crearAutomataTab.setDisable(true);
				this.automataTab.setDisable(true);
			break;
			
			case 1:
				this.crearAutomataTab.setDisable(false);
				this.automataTab.setDisable(true);
			break;
			
			case 2:
				this.inicioTab.setDisable(false);
				this.crearAutomataTab.setDisable(true);
			break;

			default:
			break;
		}
	}
	

	@FXML
	public void onClickCreateNewAutomataButton() 
	{
		this.crearAutomataTab.setDisable(false);
		this.mainTab.getSelectionModel().selectNext();
	}
	
	@FXML
	public void createAutomatonOnMouseClicked(MouseEvent event)
	{
		switch (this.currentEditionType) 
		{
		case NEWSTATE:
			this.createNewState(event);
			break;

		case NEWTRANSITION:
			this.createNewTransition(event);
			break;
		default:
			break;
		}

	}

	public void createNewState(MouseEvent event)
	{
		double coordX = event.getX();
		double coordY = event.getY();

		if (this.isStateInsidePane(coordX, coordY))
		{
			if (!this.automata.DoesStateCollision(coordX, coordY))
			{
				String name = inputStateNameDialog();
				if (name != "")
				{
					if(!this.automata.stateNameExists(name))
					{
						this.stateName = name;
						State state = new State(this.stateName, coordX, coordY);
						this.automata.addState(state);
						this.drawState(state, this.drawAreaAnchorPane);
					} 
					else 
					{
						this.ErrorAlertMessage("ERROR!" , "Input no valido", "No es posible tener nombres de estados duplicados.");
					}
				}
			}
		}
	}

	public void createNewTransition(MouseEvent event) 
	{ 
		double coordX = event.getX();
		double coordY = event.getY();

		State clickedState = this.getStateByCoords(coordX, coordY);

		if (clickedState != null)
		{
			this.numberOfTransitionClicks += 1;

			if (this.numberOfTransitionClicks == 1)
			{
				this.initialState = clickedState;
				this.firstClickedX = coordX;
				this.firstClickedY = coordY;
			}
			else if (this.numberOfTransitionClicks == 2)
			{
				this.nextState = clickedState;
				this.secondClickedX = coordX;
				this.secondClickedY = coordY;

				String name = this.inputTransitionNameDialog();
				
				if(name != "")
				{
					if(!this.automata.duplicateTransition(name.charAt(0), this.initialState.getName()))
					{
						this.transitionSymbol = name.charAt(0);
						Transition transition = new Transition(this.transitionSymbol, this.initialState, this.nextState, this.firstClickedX,this.firstClickedY, this.secondClickedX, this.secondClickedY);
						this.automata.addTransition(transition);
						this.drawTransition(transition, this.drawAreaAnchorPane);
					}
					else 
					{
						this.ErrorAlertMessage("ERROR!" , "Input no valido en un AFD", "No es posible tener el mismo simbolo que te lleve a distintos estados en un AFD.");
					}
				}
				this.numberOfTransitionClicks = 0;
			}
		}
		else 
		{
			this.numberOfTransitionClicks = 0;
		}

	}


	private boolean isStateInsidePane(double coordX, double coordY) 
	{
		if (this.drawAreaAnchorPane.getWidth() > (coordX + State.RADIUS) && 
				this.drawAreaAnchorPane.getHeight() > (coordY + State.RADIUS) &&
				(coordX - State.RADIUS >= 0) && (coordY - State.RADIUS >= 0))
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public void setEditionVariableState()
	{
		this.currentEditionType = editionType.NEWSTATE;
	}

	public void setEditionVariableTransition()
	{
		this.currentEditionType = editionType.NEWTRANSITION;
		this.numberOfTransitionClicks = 0;
	}

	public void onAutomataDelete()
	{
		boolean deleteWasAccepted = this.acceptConfirmationDialog("Confirmacion", "Esta seguro que desea borrar el automata creado?", "Si sus cambios no fueron guardados, todo su proceso sera perdido.");
		if (deleteWasAccepted)
		{
			this.automata = new Automata();
			this.drawAreaAnchorPane.getChildren().clear();
		}
	}

	public State getStateByCoords(double coordX, double coordY)
	{
		return this.automata.getStateByCoords(coordX, coordY);
	}


	public String inputStateNameDialog()
	{
		TextInputDialog dialog = new TextInputDialog("Estado");
		String nameState = "";

		dialog.setTitle("Nuevo Estado");
		dialog.setHeaderText("Ingrese el nombre del nuevo estado:");
		dialog.setContentText("Estado:");

		Optional<String> result = dialog.showAndWait();

		if(result.isPresent())
		{
			nameState = result.get();
		}
		return nameState;
	}

	public String inputTransitionNameDialog()
	{
		TextInputDialog dialog = new TextInputDialog("Transicion");
		String nameTransition = "";

		dialog.setTitle("Nueva Transicion");
		dialog.setHeaderText("Ingrese el simbolo de la transicion:");
		dialog.setContentText("Estado:");

		Optional<String> result = dialog.showAndWait();

		if(result.isPresent())
		{
			nameTransition = result.get();
			if (nameTransition.length() > 1 || nameTransition.equals(" "))
			{
				this.ErrorAlertMessage("ERROR! ", "Simbolo Invalido", "El simbolo de una transicion debe ser un caracter");
				nameTransition = "";
			}
		}
		return nameTransition;
	}
	
	// DRAWING RELATED METHODS.
	
	
	private void loadResources(AnchorPane pane) 
	{
		for (State state : this.automata.getStates()) 
		{
			drawState(state, pane);
		}
		for (Transition transition : this.automata.getTransitions()) 
		{
			drawTransition(transition, pane);
		}
	}
	
	private void drawState(State state, AnchorPane pane) 
	{
		Text text = new Text(state.getName());
		text.setFont(new Font(20));
		text.setFill(javafx.scene.paint.Color.WHITE);
		text.setBoundsType(TextBoundsType.VISUAL);

		StackPane stackPane = new StackPane();

		Circle circle = new Circle(state.getxCoord(),state.getyCoord(), State.RADIUS, javafx.scene.paint.Color.BLUE);
		stackPane.getChildren().addAll(circle, text);
		stackPane.setLayoutX(state.getxCoord() - State.RADIUS);
		stackPane.setLayoutY(state.getyCoord() - State.RADIUS);
		pane.getChildren().add(stackPane);

	}
	
	public void drawTransition(Transition transition, AnchorPane pane)
	{
		double x0 = transition.getX0Coord();
		double y0 = transition.getY0Coord();
		double x1 = transition.getX1Coord();
		double y1 = transition.getY1Coord();
		String transitionName = Character.toString(transition.getSymbol());

		Arrow arrow = new Arrow();
		arrow.setStartX(x0);
		arrow.setStartY(y0);
		arrow.setEndX(x1);
		arrow.setEndY(y1);

		Text text = new Text(transitionName);
		text.setFont(new Font(20));
		text.setFill(javafx.scene.paint.Color.BLUE);
		text.setBoundsType(TextBoundsType.VISUAL);

		StackPane stackPane = new StackPane();

		stackPane.getChildren().addAll(arrow, text);

		if(x1 < x0)
		{
			stackPane.setLayoutX(x1);
			if (y0 < y1) 
			{
				stackPane.setLayoutY(y0);
			}
			if (y1 < y0) 
			{
				stackPane.setLayoutY(y1);
			}
		}
		else if (x0 <= x1) 
		{
			stackPane.setLayoutX(x0);	
			if (y0 < y1) 
			{
				stackPane.setLayoutY(y0);
			}
			if (y1 < y0) 
			{
				stackPane.setLayoutY(y1);
			}
		}

		pane.getChildren().add(stackPane);
	}
	
	
	// FILE RELATED METHODS

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
			this.crearAutomataTab.setDisable(false);
			try {
				this.automata = this.fManager.getAutomata(fileSelected);
				this.loadResources(this.drawAreaAnchorPane);
			} catch (Exception ex) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Oops! There was a problem");
				errorAlert.setContentText("Error while retrieving the file.");
				errorAlert.showAndWait();
			}
		}
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
		this.automata = new Automata();
		this.inputString.setText("");
		boolean deleteWasAccepted = this.acceptConfirmationDialog("Confirmacion", "Esta seguro que desea crear un nuevo automata?", "Si sus cambios no fueron guardados, todo su proceso sera perdido.");
		if (deleteWasAccepted)
		{
			this.automata = new Automata();
			this.drawAreaAnchorPane.getChildren().clear();
		}
		
	}
	
	// Called when save file is called.

	@FXML
	public void saveFile() {
		TextInputDialog dialog = new TextInputDialog("myFile");
		dialog.setTitle("Filename");
		dialog.setHeaderText("To save your file we need a name.");
		dialog.setContentText("Please enter your fileName: ");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> {
			try {
				this.fManager.saveAutomata(name, this.automata);
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
	public void testWord()
	{
		this.mainTab.getSelectionModel().selectNext();
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
	public void reRender() 
	{
		this.loadRecentFiles();
	}
	
	

	// ANIMATION RELATED METHODS

	@FXML 
	public void onStartClicked() {

		if(this.btnStart.getText().equals("Start")) {
			
			this.onStartButton();
			
			String wordToTest = this.inputString.getText();
			
			boolean wordBelongsToAFD = this.automata.recgonizeWord(wordToTest, 0, this.automata.getInitialState());

			if(wordBelongsToAFD) 
			{
				this.successMessageAlert();
			} 
			else 
			{
				this.ErrorAlertMessage("ERROR!","NO SE ENCONTRO SOLUCION", "La cadena de entrada no pertenece al lenguaje generado por el automata");
				this.onStopButton();
			}
		} 
		else 
		{
			this.onStopButton();
		}


	}
	
	private void onStartButton()
	{
		this.inicioTab.setDisable(true);
		this.crearAutomataTab.setDisable(true);
		this.inputString.setDisable(true);
		this.btnStart.getStyleClass().set(1, "danger");
		this.btnStart.setText("Stop");
	}
	
	private void onStopButton()
	{
		this.inicioTab.setDisable(false);
		this.inputString.setDisable(false);
		this.crearAutomataTab.setDisable(false);
		this.btnStart.getStyleClass().set(1, "success");
		this.btnStart.setText("Start");
	}
	
	// MODALS RELATED METHODS.
	@FXML
	public void showEditComponent() {
		
	}

	@FXML 
	public void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About US!");
		alert.setHeaderText("Look for helps in the following website.");
		alert.setContentText("https://es.wikipedia.org/wiki/Aut%C3%B3mata_finito_determinista" );

		Platform.runLater(alert::showAndWait);
	}
	
	
	public boolean acceptConfirmationDialog(String title, String headerText, String contentText) 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		Optional<ButtonType> result = alert.showAndWait();
	
		if (result.get() == ButtonType.OK)
		{
			return true;
		}
		return false;
	}


	public void ErrorAlertMessage(String titleText, String headerText, String contentText)
	{
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle(titleText);
		errorAlert.setHeaderText(headerText);
		errorAlert.setContentText(contentText);
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
