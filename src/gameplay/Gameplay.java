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
import javafx.stage.StageStyle;

public class Gameplay {
	private Stage stage;
	private Scene splashScene;		// the splash scene
	private Scene gameScene;		// the game scene
	private Group root;
	private Canvas canvas;			// the canvas where the animation happens
	
	public final static int WINDOW_WIDTH = 400;
	public final static int WINDOW_HEIGHT = 700;
	
	public Gameplay() {
		this.canvas = new Canvas( Gameplay.WINDOW_WIDTH, Gameplay.WINDOW_HEIGHT );
		this.root = new Group();
        this.root.getChildren().add( this.canvas );
        this.gameScene = new Scene( root );
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle( "Tamaan Mo Ko Beybe" );
        
		this.initSplash(stage);			// initializes the Splash Screen with the New Game button
		
		this.stage.setScene( this.splashScene );
        this.stage.setResizable(false);
		this.stage.show();
		//this.setGame(stage);
	}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createVBox(),this.createAboutButton(200, 400),this.createDevelopersButton(200, 500),this.createPlayButton(200, 300));
        this.splashScene = new Scene(root);
	}
	
	void setGame(Stage stage) {
        stage.setScene( this.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameplayTimer gameTimer = new GameplayTimer(gc, gameScene);
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
        
	}	
	
	private Canvas createCanvas() {
    	Canvas canvas = new Canvas(Gameplay.WINDOW_WIDTH,Gameplay.WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image bg = new Image("images/somethings.jpg");
        gc.drawImage(bg, 0,0,Gameplay.WINDOW_WIDTH,Gameplay.WINDOW_HEIGHT);
        
        return canvas;
    }
	
	private VBox createVBox() {
		VBox box = new VBox();
		ImageView imgView = new ImageView("images/menubg.gif");
		imgView.setFitWidth(WINDOW_WIDTH);
		imgView.setFitHeight(WINDOW_HEIGHT);
		
		
		box.getChildren().addAll(imgView);
		return box;
	}
	
	private VBox createPlayButton(double x, double y) {
		VBox box = new VBox();		
		Button b1 = new Button("Play");
		b1.setTranslateX(x);
		b1.setTranslateY(y);
		b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
		box.getChildren().add(b1);
		return box;
	}
	
	private VBox createAboutButton(double x, double y) {
		VBox box = new VBox();
		Button b1 = new Button("About");
		b1.setTranslateX(x);
		b1.setTranslateY(y);
		b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
		box.getChildren().add(b1);
		return box;
	}
	
	private VBox createDevelopersButton(double x, double y) {
		VBox box = new VBox();
		Button b1 = new Button("Developers");
		b1.setTranslateX(x);
		b1.setTranslateY(y);
		
		box.getChildren().add(b1);
		return box;
	}
	
	
	

}
