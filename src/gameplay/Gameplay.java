package gameplay;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gameplay {
	private Stage stage;
	private Group root;
	private Canvas canvas;			// the canvas where the animation happens
	private StackPane gameStackPane;
	private static StackPane endStackPane;
	
	private static Scene splashScene;		// the splash scene
	private static Scene aboutScene;
	private static Scene developerScene;
	private static Scene gameScene;		// the game scene
	private static Scene endScene;
	
	public final static int WINDOW_WIDTH = 400;
	public final static int WINDOW_HEIGHT = 700;
	
	public Gameplay() {
		
		this.canvas = new Canvas( Gameplay.WINDOW_WIDTH, Gameplay.WINDOW_HEIGHT );
		this.root = new Group();
		this.gameStackPane = new StackPane();
		this.gameStackPane.getChildren().add(this.canvas);
        this.root.getChildren().add( gameStackPane );
        
        Gameplay.gameScene = new Scene( root );
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle( "Tamaan Mo Ko Beybe" );
		this.stage.getIcons().add(new Image("images/bola.png"));
        
		this.initSplash(stage);			// initializes the Splash Screen with the New Game button
		this.initEnd(stage);
		this.initAbout(stage);
		this.initDevelopers(stage);
		
		this.stage.setScene( Gameplay.splashScene );
        this.stage.setResizable(false);
		this.stage.show();
		//this.setGame(stage);
	}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
		
		Function startGame = () -> setGame(stage);	//acts as a function storage
		Button startButton = this.createButton(0, 0, "Play", "white", "#fe5502", "#ff945f" ,startGame);
		
		Function showAbout = () -> setAbout(stage);
		Button aboutButton = this.createButton(0, 100, "About", "#7b5201", "#ffbc09", "#fee393" ,showAbout);
		
		Function showDevelopers = () -> setDevelopers(stage);
		Button developersButton = this.createButton(0, 200, "Developers", "#7b5201", "#ffbc09", "#fee393" ,showDevelopers);
		
		root.getChildren().addAll(this.createVBox("images/menubg.gif"),startButton,aboutButton,developersButton);
		Gameplay.splashScene = new Scene(root);
	}

	private void initEnd(Stage stage) {
		StackPane root = new StackPane();
		Function backToMenu = () -> setHome(stage);
		Button menuButton = this.createButton(0, 100, "Play Again", "white", "#fe5502", "#ff945f" ,backToMenu);
		
		root.getChildren().addAll(this.createVBox("images/plainBackground.png"),menuButton);
		Gameplay.endStackPane = root;
		Gameplay.endScene = new Scene(root);
	}
	
	
	private void initAbout(Stage stage) {
		StackPane root = new StackPane();
		
		/** Insert code here
		 * 
		 * 
		 * 
		 * */
		
		root.getChildren().addAll(this.createVBox("images/plainBackground.png"));
		Gameplay.aboutScene = new Scene(root);
	}
	
	private void initDevelopers(Stage stage) {
		StackPane root = new StackPane();
		/** Insert code here
		 * 
		 * 
		 * 
		 * */
		root.getChildren().addAll(this.createVBox("images/plainBackground.png"));
		Gameplay.developerScene = new Scene(root);
	}
	
	void setGame(Stage stage) {
        stage.setScene( Gameplay.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameplayTimer gameTimer = new GameplayTimer(gc, this.gameStackPane,gameScene,stage);
        gameTimer.setStartTime(System.currentTimeMillis());
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
        
	}	
	
	private void setDevelopers(Stage stage) {
		stage.setScene( Gameplay.developerScene );	
	}
	
	private void setAbout(Stage stage) {
		stage.setScene( Gameplay.aboutScene );	
	}
	
	//go back to game over screen
	public static void setGameOver(Stage stage,boolean isTayaWinner) {
		ImageView tayaWins;
		if(isTayaWinner) {
			tayaWins = new ImageView("images/tayaWins.gif");
		} else {
			tayaWins = new ImageView("images/throwerWins.gif");
		}
		Gameplay.endStackPane.getChildren().add(tayaWins);
        stage.setScene( Gameplay.endScene );	
        
	}
	
	//go back to home screen
	public static void setHome(Stage stage) {
		//remove image view in game over screen to reset
		if(Gameplay.endStackPane.getChildren().size() > 2) {
			Gameplay.endStackPane.getChildren().remove(2);
		}
        stage.setScene( Gameplay.splashScene );	
	}
	
	//mainly for background
	private VBox createVBox(String imagePath) {
		VBox box = new VBox();
		ImageView imgView = new ImageView(imagePath);
		imgView.setFitWidth(WINDOW_WIDTH);
		imgView.setFitHeight(WINDOW_HEIGHT);
		
		
		box.getChildren().addAll(imgView);
		return box;
	}
	
	
	//creates a button
	private Button createButton(double x, double y, String text, String textColor, String hexColor1, String hexColor2, Function func) {
		// Paggawa ng button
	    Button button = new Button(text);
	    
	    //set position
	    button.setTranslateX(x);
	    button.setTranslateY(y);
	    
	    // kulay nung button and style
	    button.setStyle(
            "-fx-background-color: linear-gradient(" + hexColor1 + "," + hexColor2 + "); " +
            "-fx-text-fill: " + textColor + "; " +
            "-fx-font-size: 18px; " +
            "-fx-padding: 10px 20px;" +
            "-fx-background-radius: 15px;" +
            "-fx-min-width: 200px;" +
            "-fx-font-weight: bold;"
	    );

	    // magdadarken kapag hinover yung mouse sa button
	    button.setOnMouseEntered(e -> {
	        button.setStyle(
                "-fx-background-color: " + hexColor1 + ";" +  // Darkened color
                "-fx-text-fill: " + textColor + "; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 10px 20px;" +
	            "-fx-background-radius: 15px;" +
	            "-fx-min-width: 200px;" +
	            "-fx-font-weight: bold;"

	        );
	    });

	    // babalik sa dating kulay kapag wala yung mouse sa button
	    button.setOnMouseExited(e -> {
	        button.setStyle(
        		"-fx-background-color: linear-gradient(" + hexColor1 + "," + hexColor2 + "); " +
        	    "-fx-text-fill: " + textColor + "; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 10px 20px;" +
	            "-fx-background-radius: 15px;" +
	            "-fx-min-width: 200px;" +
	            "-fx-font-weight: bold;"

	        );
	    });
	    
	    button.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                func.apply();;		// changes the scene into the game scene
            }
        });

		return button;
	}
	

}