/*************************************************************************************************************************
 *
 * Gameplay 
 * The main gameplay of the game including menu and the game loop
 * Also manages the stage and scenes
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/

package gameplay;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gameplay {
	// essentials
	private Stage stage;
	private Group root;
	private Canvas canvas;			
	
	//Stack Panes
	private StackPane gameStackPane;
	private static StackPane endStackPane;
	
	//Scenes
	private static Scene splashScene;		
	private static Scene aboutScene;
	private static Scene developerScene;
	private static Scene gameScene;		
	private static Scene endScene;
	
	//constants
	public final static int WINDOW_WIDTH = 400;
	public final static int WINDOW_HEIGHT = 700;
	
	//constructor
	public Gameplay() {
		this.canvas = new Canvas( Gameplay.WINDOW_WIDTH, Gameplay.WINDOW_HEIGHT );
		this.root = new Group();
		this.gameStackPane = new StackPane();
		this.gameStackPane.getChildren().add(this.canvas);
        this.root.getChildren().add( gameStackPane );
        
        Gameplay.gameScene = new Scene( root );
	}
	
	//set up the stage
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle( "Tamaan Mo Ko Beybe" );
		this.stage.getIcons().add(new Image("images/bola.png"));
        
		//initialize all scenes for the stage
		this.initSplash(stage);			
		this.initEnd(stage);
		this.initAbout(stage);
		this.initDevelopers(stage);
		
		//initial scene setup
		this.stage.setScene( Gameplay.splashScene );
        this.stage.setResizable(false);
		this.stage.show();
		
		//play music
		SoundHandler.mediaPlayer = new MediaPlayer(SoundHandler.menuMusic);
		SoundHandler.mediaPlayer.play();
	}
	
	//initialize menu/splash screen
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
	
	//initialize end scene (the scene after the game)
	private void initEnd(Stage stage) {
		StackPane root = new StackPane();
		Function backToMenu = () -> setHome(stage,true);
		Button menuButton = this.createButton(0, 100, "Play Again", "white", "#fe5502", "#ff945f" ,backToMenu);
		
		root.getChildren().addAll(this.createVBox("images/plainBackground.png"),menuButton);
		Gameplay.endStackPane = root;
		Gameplay.endScene = new Scene(root);
	}
	
	//initialize about page
	private void initAbout(Stage stage) {
		StackPane root = new StackPane();
		
		Function goBack = () -> setHome(stage,false); //para makabalik sa home
		Button backButton = this.createButton(0, 300, "Go Back", "white", "#fe5502", "#ff945f", goBack);

		Text gameTitleText = new Text("Game Title: Tamaan Mo Ko Beybe\n" +
		        "Genre: 2-Player Arcade\n\n");
	    Text gameInfoText = new Text(
	        "Objectives: \n" + 
	        "This is a two-player game consisting of a\nthrowing team and a dodger. The goal of the \nthrowing team is to hit the dodger with the ball \nbefore the time runs out. The goal \nof the dodger is to dodge the ball \nuntil the time runs out.\n\n" +
	        "Overview: \n" +
	        "In the Philippines, Tamaang Tao (Dodgeball) \nis widely played by Filipinos of all ages. \nIt is basically a group game where the tagging \nteam(taya) aims to eliminate opponents \nby throwing a ball at dodgers. \nTarget players (pain) are in \nthe middle of the rectangular playing \narea while tagging teams are located at \nthe end lines. To get the point, the \nthrowers must strike the dodger within \na set amount of time, while the dodgers must \ndodge the ball until the time runs out.\n\n" +
	        "Controls:\n" +
	        "Throwing team - \nMouse position, mouse left and right button, \nleft and right arrow keys\n" +
	        "Dodger - \nW, A, S, D keys"
	    );
	    
	    gameTitleText.setTranslateY(-275);
	    gameTitleText.setFill(Color.BLACK);
	    gameTitleText.setFont(Font.font("System", FontWeight.BOLD, 20));
	    
	    gameInfoText.setTranslateY(0);
	    gameInfoText.setFill(Color.BLACK);
	    gameInfoText.setFont(Font.font("System", FontWeight.BOLD, 14));  


	    root.getChildren().addAll(this.createVBox("images/plainBackground.png"), backButton, gameTitleText, gameInfoText);
		
		Gameplay.aboutScene = new Scene(root);
	}
	
	//initialize developers page
	private void initDevelopers(Stage stage) {
		StackPane root = new StackPane();
		 
		VBox textContainer = new VBox();
        //textContainer.setAlignment(Pos.CENTER); 
        textContainer.setSpacing(10);

        // Add images above each name
        ImageView jomarImage = new ImageView(new Image("images/jom.png"));
        jomarImage.setFitWidth(100); 
        jomarImage.setFitHeight(100); 
        ImageView alessandroImage = new ImageView(new Image("images/marcus.png"));
        alessandroImage.setFitWidth(100); 
        alessandroImage.setFitHeight(100); 
        ImageView jamesImage = new ImageView(new Image("images/james.png"));
        jamesImage.setFitWidth(100); 
        jamesImage.setFitHeight(100); 

        VBox jomarContainer = new VBox();
        jomarContainer.setAlignment(Pos.CENTER); 
        jomarContainer.getChildren().addAll(jomarImage, createStyledText("Name: Monreal Jomar P.\n" +
                "Age: 19\n" +
                "Motto: Hangga't mayroon, may gagawin\n" +
                "Description: Jomar Monreal is a college boi who likes to \nwander anywhere as long as there's something to move. \nIf I can move my legs, I need to walk. If I can move my \nhands, I need to do something. I'm an active inertia."));
        
        VBox alessandroContainer = new VBox();
        alessandroContainer.setAlignment(Pos.CENTER); 
        alessandroContainer.getChildren().addAll(alessandroImage, createStyledText("Name: Ocampo, Alessandro Marcus M.\n" +
                "Age: 20\n" +
                "Motto: shawty is an eenie meanie miny mo lava\n" +
                "Description: My hobbies are sleeping and eating and i am a \nprofessional rapper. Because I was once a young boy and \nno I dont play with toys eyyy. Anyways that's a clip \nand i'll see you on the flip side."));
        
        VBox jamesContainer = new VBox();
        jamesContainer.setAlignment(Pos.CENTER);
        jamesContainer.getChildren().addAll(jamesImage, createStyledText("Name: Villarosa, James Carl V.\n" +
                "Age: 19\n" +
                "Motto: Hangga't may kabutihang asal sa mundo,\nmay munggo\n" +
                "Decription: My name is James, simple lang at medyo may \nangas.I like computers, badminton, guitar, and I like \nthe way she calls my name, jems."));

        VBox imageAndNameContainer = new VBox();
        imageAndNameContainer.setAlignment(Pos.CENTER);
        imageAndNameContainer.getChildren().addAll(jomarContainer, alessandroContainer, jamesContainer);
        
        
        Function goBack = () -> setHome(stage,false); //para makabalik sa home
		Button backButton = this.createButton(0, 300, "Go Back", "white", "#fe5502", "#ff945f", goBack);
		
		
        root.getChildren().add(this.createVBox("images/plainBackground.png"));
        root.getChildren().add(textContainer);
        root.getChildren().add(backButton);
        
        ((VBox) root.getChildren().get(1)).getChildren().add(0, imageAndNameContainer);
		Gameplay.developerScene = new Scene(root);
	}
	
	//stylizes the text
	private Text createStyledText(String content) {
        Text styledText = new Text(content);
        styledText.setFill(Color.BLACK);
        styledText.setFont(Font.font("System", FontWeight.BOLD, 12));
        styledText.setLineSpacing(-3);
        return styledText;
    }
	
	//set scene into the main game loop
	void setGame(Stage stage) {
        stage.setScene( Gameplay.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameplayTimer gameTimer = new GameplayTimer(gc, this.gameStackPane,gameScene,stage);
        gameTimer.setStartTime(System.currentTimeMillis());
        SoundHandler.mediaPlayer.stop();
        SoundHandler.mediaPlayer = new MediaPlayer(SoundHandler.gameplayMusic);
        SoundHandler.mediaPlayer.play();
        
        // this internally calls the handle() method of our GameplayTimer
        gameTimer.start();
	}	
	
	//change scene to developer
	private void setDevelopers(Stage stage) {
		stage.setScene( Gameplay.developerScene );	
	}
	
	//change scene to about
	private void setAbout(Stage stage) {
		stage.setScene( Gameplay.aboutScene );	
	}
	
	//change into end scene / game over scene
	public static void setGameOver(Stage stage,boolean isTayaWinner) {
		//show corresponding congratulatory message for the winner
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
	public static void setHome(Stage stage, boolean hasPlayed) {
		//the user just played, go back to menu music so it won't reset
		if(hasPlayed) {
			SoundHandler.mediaPlayer.stop();
			SoundHandler.mediaPlayer = new MediaPlayer(SoundHandler.menuMusic);
			SoundHandler.mediaPlayer.play();
		}
		
		//remove image view in game over screen to reset
		if(Gameplay.endStackPane.getChildren().size() > 2) {
			Gameplay.endStackPane.getChildren().remove(2);
		}
        stage.setScene( Gameplay.splashScene );	
	}
	
	//mainly for title screen background
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
	    
	    //do Function when pressed
	    button.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                func.apply();;		// changes the scene into the game scene
            }
        });

		return button;
	}
	

}