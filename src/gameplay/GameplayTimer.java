/*************************************************************************************************************************
 *
 * Gameplay Timer
 * Acts as the main game loop handler of the whole game
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/

package gameplay;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameplayTimer extends AnimationTimer{
	//all in milliseconds
	private long startTime = 0;
	private long timeLeft;
	private long secondsLeft = 0;
	private long gameTimeLength = 20000;
	private long delayBeforeFinish = 2500;
	
	//flags
	private boolean hasFinished = false;
	private boolean isTayaWinner = false;
	
	//internal nodes
	private GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private StackPane stackPane;
	
	//essential images
	private Image background = new Image("images/bg.jpg");
	private Image throwerImage1 = new Image("images/charac1shadow.png");
	private Image throwerImage2 = new Image("images/charac2shadow.png");
	private Image ballImage = new Image("images/bola.png");
	private double characterSize = 60;
	
	//for throwers
	private static Thrower thrower1;
	private static Thrower thrower2;	
	private double throwerStartingX;
	
	//for ball
	private static Ball ball;
	private double ballBoundsMarginX = 80;
	private double ballBoundsMarginY = 80;
	
	//for taya
	private static Taya taya;
	private Image tayaHarap = new Image("images/tayaharap1.png");
	private Image tayaLikod = new Image("images/tayalikod1.png");
	private Image tayaLeft = new Image("images/tayaleft1.png");
	private Image tayaRight = new Image("images/tayaright1.png");
	private Image tayaGotHit = new Image("images/tayapatay.png");
	
	//constructor
	public GameplayTimer(GraphicsContext gc, StackPane stackPane, Scene scene, Stage stage) {
		this.gc = gc;
		this.scene = scene;
		this.stage = stage;
		this.timeLeft = this.gameTimeLength;
		this.stackPane = stackPane;
		this.prepareActionHandlers();
		
		//initializing throwers
		this.throwerStartingX = Gameplay.WINDOW_WIDTH/2 - this.characterSize/2;
		GameplayTimer.thrower1 = new Thrower(this.throwerStartingX, 575, this.characterSize, this.throwerImage1,10,true);
		GameplayTimer.thrower2 = new Thrower(this.throwerStartingX, 60, this.characterSize, this.throwerImage2,10,false);
		
		//initializing ball
		GameplayTimer.ball = new Ball(GameplayTimer.thrower1.getXPos() + 30, GameplayTimer.thrower1.getYPos()-(this.characterSize/2), 30, this.ballImage,10);
		
		//initializing taya
		double randomX = Math.random() * 200 +70;
		double randomY = Math.random() * 310 +170;
		GameplayTimer.taya = new Taya(randomX,randomY,this.characterSize * 380/450,this.characterSize,this.tayaHarap,9);
		GameplayTimer.taya.initDirectionalImages(this.tayaHarap, this.tayaLikod, this.tayaLeft, this.tayaRight, this.tayaGotHit);
	}
	
	//event handlers setup
	private void prepareActionHandlers() {
    	
		//when mouse is clicked
    	this.scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
            {
    			if (event.getButton() == MouseButton.PRIMARY) { // if left mouse button clicked
    				if(GameplayTimer.ball.isMoving()) {
    					return;
    				}
    				
    				// update the starting posiiton of ball relative to thrower
    				if(GameplayTimer.thrower1.isHoldingBall()) {
    					GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.ball.getRadius());
    					GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.thrower1.height/2));
    				} else {
    					GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.ball.getRadius());
    					GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.thrower2.height);
    				}
    				
    				//then get the normalized vector for movement
    				double mouseX = event.getX();
    		        double mouseY = event.getY();
    				GameplayTimer.ball.setNormalizedVector(mouseX, mouseY);
                }
                
            }
    	});
    	
    	//when key is pressed
    	this.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e)
            {
				//handles thrower movement
				if (e.getCode() == KeyCode.RIGHT) {
					GameplayTimer.thrower1.setDX(GameplayTimer.thrower1.getSpeed());
					GameplayTimer.thrower2.setDX(GameplayTimer.thrower2.getSpeed());
				} 	
				else if (e.getCode() == KeyCode.LEFT) {
					GameplayTimer.thrower1.setDX(GameplayTimer.thrower1.getSpeed() * -1);
					GameplayTimer.thrower2.setDX(GameplayTimer.thrower2.getSpeed() * -1);
				}	
				
				//handles taya movement
				if (e.getCode() == KeyCode.W) {
					GameplayTimer.taya.setDY(GameplayTimer.taya.getSpeed() * -1);
				} else if (e.getCode() == KeyCode.S) {
					GameplayTimer.taya.setDY(GameplayTimer.taya.getSpeed());
				} else if (e.getCode() == KeyCode.D) {
					GameplayTimer.taya.setDX(GameplayTimer.taya.getSpeed());
				} else if (e.getCode() == KeyCode.A) {
					GameplayTimer.taya.setDX(GameplayTimer.taya.getSpeed() * -1);
				} 
				
				
            }
				
				
        });
    	
    	//when key is released
    	this.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				//handles thrower movement
				if (e.getCode() == KeyCode.RIGHT) {
					GameplayTimer.thrower1.setDX(0);
					GameplayTimer.thrower2.setDX(0);
				} 	
				else if (e.getCode() == KeyCode.LEFT) {
					GameplayTimer.thrower1.setDX(0);
					GameplayTimer.thrower2.setDX(0);
				}	
				
				//handles taya movement
				if (e.getCode() == KeyCode.W) {
					GameplayTimer.taya.setDY(0);
				} else if (e.getCode() == KeyCode.S) {
					GameplayTimer.taya.setDY(0);
				} else if (e.getCode() == KeyCode.D) {
					GameplayTimer.taya.setDX(0);
				} else if (e.getCode() == KeyCode.A) {
					GameplayTimer.taya.setDX(0);
				} 
				
			}
    		
    	});
    	
    	
    }

	//overriding handle from AnimationTimer
	@Override
	public void handle(long currentNanoTime) {
		//keeps track of elapsed time
		long elapsedTime = System.currentTimeMillis() - this.startTime;
		
		//do this when game is finished
		if(this.hasFinished) {
			//wait for seconds before showing gameover screen
			if(elapsedTime > this.delayBeforeFinish) {
				Gameplay.setGameOver(stage,this.isTayaWinner);
				if(this.stackPane.getChildren().size() > 1) {
					this.stackPane.getChildren().remove(1);
				}
				this.stop();
			}
			return;
		}
		
		//do this while game hasn't yet finished
		this.moveSprites();
		this.render();
		
		//time handler
		this.timeLeft = this.gameTimeLength - elapsedTime;
		this.secondsLeft = (int) (timeLeft/1000) % 60;
		
		//when taya was hit by ball
		if(GameplayTimer.ball.collidesWith(GameplayTimer.taya)) {
			GameplayTimer.ball.setMoving(false);
			GameplayTimer.taya.setImage(GameplayTimer.taya.getGotHit());
			this.render();
			
			SoundHandler.mediaPlayer.stop();
			SoundHandler.mediaPlayer = new MediaPlayer(SoundHandler.deathSound);
			SoundHandler.mediaPlayer.play();
						
			this.hasFinished = true;
			this.isTayaWinner = false;
			this.startTime = System.currentTimeMillis(); //reset timer
		}
		
		//when taya survives the round
		else if(this.timeLeft <= 0) {
			GameplayTimer.ball.setMoving(false);	
			ImageView timesUp = new ImageView("/images/time_sUp.gif");
			timesUp.setScaleX(1.2);
			timesUp.setScaleY(1.2);
			timesUp.setTranslateY(70);
			
			this.stackPane.getChildren().add(timesUp);
			this.hasFinished = true;
			this.isTayaWinner = true;
			this.startTime = System.currentTimeMillis(); //reset timer
		}
		
        
	}
	
	//move the sprites
	private void moveSprites() {
		this.moveTaya();
		this.moveBall();
		this.moveThrowers();
	}
	
	//then re-render
	private void render() {
		//clear canvas
		gc.clearRect(0, 0, Gameplay.WINDOW_WIDTH, Gameplay.WINDOW_HEIGHT);
		
		//then redraw background
		gc.drawImage(background, 0, 0, Gameplay.WINDOW_WIDTH,Gameplay.WINDOW_HEIGHT);
		
		//redraw timer
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD,15));
		String timeDisplayText = "Time remaining: " + Long.toString(secondsLeft) + " seconds left";
		gc.setFill(Color.WHITE);
		gc.fillText(timeDisplayText, 60, 40);
		
		//redraw sprites
		GameplayTimer.thrower1.render(this.gc);
		GameplayTimer.thrower2.render(this.gc);
		GameplayTimer.taya.render(this.gc);
		GameplayTimer.ball.render(gc);
	}
	
	//move throwers
	private void moveThrowers() {
		//when thrower dx is zero, do nothing
		if(GameplayTimer.thrower1.getDX() == 0) {
			return;
		}
		//if dx is positive, go right
		else if (GameplayTimer.thrower1.getDX() > 0) {
			if(GameplayTimer.thrower1.getXPos() < 270) {
				GameplayTimer.thrower1.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.thrower1.getDX());
				GameplayTimer.thrower2.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.thrower2.getDX());
			}
		} 	
		//if dy is negative, go left
		else if (GameplayTimer.thrower1.getDX() < 0) {
			if(GameplayTimer.thrower1.getXPos() > 70) {
				GameplayTimer.thrower1.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.thrower1.getDX());
				GameplayTimer.thrower2.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.thrower2.getDX());
			}
		}	
		
		//handles ball movement
		GameplayTimer.followActiveThrower();
		
	}
	
	// move taya
	private void moveTaya() {
		//do nothing when dx and dy = 0
		if (GameplayTimer.taya.getDX() == 0 && GameplayTimer.taya.getDY() == 0){
			return;
		}
		//if moving up
		if(GameplayTimer.taya.getDY() < 0){
			if(GameplayTimer.taya.getYPos() > 170) {
				GameplayTimer.taya.setYPos(GameplayTimer.taya.getYPos() + GameplayTimer.taya.getDY());
				GameplayTimer.taya.setImage(GameplayTimer.taya.getLikod());
			}
		} 
		//if moving down
		if (GameplayTimer.taya.getDY() > 0) {
			if(GameplayTimer.taya.getYPos() < 480) {
				GameplayTimer.taya.setYPos(GameplayTimer.taya.getYPos() + GameplayTimer.taya.getDY());
				GameplayTimer.taya.setImage(GameplayTimer.taya.getHarap());
			}
		}
		//if moving right
		if (GameplayTimer.taya.getDX() > 0) {
			if(GameplayTimer.taya.getXPos() < 270) {
				GameplayTimer.taya.setXPos(GameplayTimer.taya.getXPos() + GameplayTimer.taya.getDX());
				GameplayTimer.taya.setImage(GameplayTimer.taya.getRight());
			}
		}
		//if moving left
		if (GameplayTimer.taya.getDX() < 0) {
			if(GameplayTimer.taya.getXPos() > 70) {
				GameplayTimer.taya.setXPos(GameplayTimer.taya.getXPos() + GameplayTimer.taya.getDX());
				GameplayTimer.taya.setImage(GameplayTimer.taya.getLeft());
			}
		} 
	}
	
	//move ball
	private void moveBall() {
		//do nothing when ball is moving
		if(GameplayTimer.ball.isMoving() == false) {
			return;
		}
		
		//when ball hits the first thrower
		if(GameplayTimer.ball.collidesWith(GameplayTimer.thrower1)) {
			GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos());
			GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.thrower1.height/2));
			GameplayTimer.ball.setMoving(false);
			GameplayTimer.thrower1.setHoldingBall(true);
			GameplayTimer.thrower2.setHoldingBall(false);
			return;
		} 
		//when ball hits the second thrower
		else if(GameplayTimer.ball.collidesWith(GameplayTimer.thrower2)) {
			GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos());
			GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.thrower2.height);
			GameplayTimer.ball.setMoving(false);
			GameplayTimer.thrower1.setHoldingBall(false);
			GameplayTimer.thrower2.setHoldingBall(true);
			return;
		}
		
		
		//update position based on speed
		GameplayTimer.ball.setXPos(GameplayTimer.ball.getXPos() + GameplayTimer.ball.getNormalizedVectorX() * GameplayTimer.ball.getSpeed());
		GameplayTimer.ball.setYPos(GameplayTimer.ball.getYPos() + GameplayTimer.ball.getNormalizedVectorY() * GameplayTimer.ball.getSpeed());
		
		//bouncing mechanics
		if (GameplayTimer.ball.getXPos() < this.ballBoundsMarginX-20 || GameplayTimer.ball.getXPos() > Gameplay.WINDOW_WIDTH-this.ballBoundsMarginX) {
			GameplayTimer.ball.setNormalizedVectorX(GameplayTimer.ball.getNormalizedVectorX() * -1);	//reverse x direction
		}
		if(GameplayTimer.ball.getYPos() < this.ballBoundsMarginY || GameplayTimer.ball.getYPos() > Gameplay.WINDOW_HEIGHT-this.ballBoundsMarginY) {
			GameplayTimer.ball.setNormalizedVectorY(GameplayTimer.ball.getNormalizedVectorY() * -1);	//reverse y direction
		}
	
	}
	
	
	
	// when the ball is not moving, just let it follow the thrower holding it
	private static void followActiveThrower() {
		if(!GameplayTimer.ball.isMoving()) {
			if(GameplayTimer.thrower1.isHoldingBall()) {
				GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos());
				GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.thrower1.height/2));
			} else {
				GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos());
				GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.thrower2.height);
			}
			
		}
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
