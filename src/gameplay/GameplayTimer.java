package gameplay;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
//import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameplayTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private Image background = new Image("images/bg.jpg");
	private Image throwerImage1 = new Image("images/charac1shadow.png");
	private Image throwerImage2 = new Image("images/charac2shadow.png");
	private Image ballImage = new Image("images/bola.png");
	
	//for throwers
	private static Thrower thrower1;
	private static Thrower thrower2;
	private static double throwerSpeed = 10;
	private static double throwerX;
	private static double throwerSize = 60;
	private static boolean isThrower1Active = true;
	
	
	//for ball
	private static Ball ball;
	private static double mouseX = 0;
	private static double mouseY = 0;
	private static double normalizedDirectionX = 0;
	private static double normalizedDirectionY = 0;
	private static boolean isBallMoving = false;
	private double throwSpeed = 8.0;
	private double ballBoundsMarginX = 80;
	private double ballBoundsMarginY = 80;
	private static double ballRadius = 30;
	
	//for taya
	private static Taya taya;
	private Image tayaHarap = new Image("images/tayaharap.png");
	private Image tayaLikod = new Image("images/tayalikod.png");
	private Image tayaLeft = new Image("images/tayaleft.png");
	private Image tayaRight = new Image("images/tayaright.png");
	
	public GameplayTimer(GraphicsContext gc, Scene scene, Stage stage) {
		this.gc = gc;
		this.scene = scene;
		this.stage = stage;
		
		GameplayTimer.throwerX = Gameplay.WINDOW_WIDTH/2 - GameplayTimer.throwerSize/2;
		GameplayTimer.thrower1 = new Thrower(GameplayTimer.throwerX, 575, GameplayTimer.throwerSize, GameplayTimer.throwerSize, this.throwerImage1);
		GameplayTimer.thrower2 = new Thrower(GameplayTimer.throwerX, 60, GameplayTimer.throwerSize, GameplayTimer.throwerSize, this.throwerImage2);
		GameplayTimer.ball = new Ball(GameplayTimer.thrower1.getXPos() + GameplayTimer.ballRadius,GameplayTimer.thrower1.getYPos()-(GameplayTimer.throwerSize/2),GameplayTimer.ballRadius,this.ballImage);
		GameplayTimer.taya = new Taya(200,350,GameplayTimer.throwerSize,GameplayTimer.throwerSize,this.tayaHarap,10);
		GameplayTimer.taya.initDirectionalImages(this.tayaHarap, this.tayaLikod, this.tayaLeft, this.tayaRight);
		this.prepareActionHandlers();
	}


	@Override
	public void handle(long currentNanoTime) {
        this.moveSprites();
        this.renderSprites();
	}

	private void prepareActionHandlers() {
    	
    	this.scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
            {
    			if (event.getButton() == MouseButton.PRIMARY) { // if left mouse button clicked
    				if(GameplayTimer.isBallMoving) {
    					return;
    				}
    				
    				// update the position
    				if(GameplayTimer.isThrower1Active) {
    					GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.ballRadius);
    					GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.throwerSize/2));
    				} else {
    					GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.ballRadius);
    					GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.throwerSize);
    				}
    				
    				GameplayTimer.mouseX = event.getX();
    		        GameplayTimer.mouseY = event.getY();
    			 
    				// find direction of ball thrown when mouse clicked  
    				double directionX = GameplayTimer.mouseX - GameplayTimer.ball.getXPos();
    				double directionY = GameplayTimer.mouseY - GameplayTimer.ball.getYPos();
    				
    				// get magnitude to be used for computing new direction for x and y
    				double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
    				
    				// get unit vector for x and y
    				GameplayTimer.normalizedDirectionX = directionX / magnitude;
    				GameplayTimer.normalizedDirectionY = directionY / magnitude;
    		        
    		        GameplayTimer.isBallMoving = true;
                }
                
            }
    	});
    	
    	
    	this.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e)
            {
				//handles thrower movement
				if (e.getCode() == KeyCode.RIGHT) {
					if(GameplayTimer.thrower1.getXPos() < 270) {
						GameplayTimer.thrower1.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.throwerSpeed);
						GameplayTimer.thrower2.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.throwerSpeed);
					}
				} 	
				else if (e.getCode() == KeyCode.LEFT) {
					if(GameplayTimer.thrower1.getXPos() > 70) {
						GameplayTimer.thrower1.setXPos(GameplayTimer.thrower1.getXPos() - GameplayTimer.throwerSpeed);
						GameplayTimer.thrower2.setXPos(GameplayTimer.thrower2.getXPos() - GameplayTimer.throwerSpeed);
					}
				}	
				GameplayTimer.followActiveThrower();
				
				//handles taya movement
				if (e.getCode() == KeyCode.W) {
					if(GameplayTimer.taya.getYPos() > 170) {
						GameplayTimer.taya.setYPos(GameplayTimer.taya.getYPos() - GameplayTimer.taya.getSpeed());
						GameplayTimer.taya.setImage(GameplayTimer.taya.getLikod());
					}
				} else if (e.getCode() == KeyCode.S) {
					if(GameplayTimer.taya.getYPos() < 480) {
						GameplayTimer.taya.setYPos(GameplayTimer.taya.getYPos() + GameplayTimer.taya.getSpeed());
						GameplayTimer.taya.setImage(GameplayTimer.taya.getHarap());
					}
				} else if (e.getCode() == KeyCode.D) {
					if(GameplayTimer.taya.getXPos() < 270) {
						GameplayTimer.taya.setXPos(GameplayTimer.taya.getXPos() + GameplayTimer.taya.getSpeed());
						GameplayTimer.taya.setImage(GameplayTimer.taya.getRight());
					}
				} else if (e.getCode() == KeyCode.A) {
					if(GameplayTimer.taya.getXPos() > 70) {
						GameplayTimer.taya.setXPos(GameplayTimer.taya.getXPos() - GameplayTimer.taya.getSpeed());
						GameplayTimer.taya.setImage(GameplayTimer.taya.getLeft());
					}
				} 
				
				
            }
				
				
        });
    	
    	
    }
	
	private void moveSprites() {
		this.moveBall(this.stage);
	}


	private void moveBall(Stage stage) {
		if(GameplayTimer.isBallMoving == false) {
			return;
		}
		if(GameplayTimer.ball.collidesWith(GameplayTimer.taya)) {
			Gameplay.setGameOver(stage);
			GameplayTimer.isBallMoving = false;
		}
		if(GameplayTimer.ball.collidesWith(GameplayTimer.thrower1)) {
			GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.ballRadius);
			GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.throwerSize/2));
			GameplayTimer.isBallMoving = false;
			GameplayTimer.isThrower1Active = true;
			return;
		} else if(GameplayTimer.ball.collidesWith(GameplayTimer.thrower2)) {
			GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.ballRadius);
			GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.throwerSize);
			GameplayTimer.isBallMoving = false;
			GameplayTimer.isThrower1Active = false;
			return;
		}
		
		
		//update position based on speed
		GameplayTimer.ball.setXPos(GameplayTimer.ball.getXPos() + GameplayTimer.normalizedDirectionX * this.throwSpeed);
		GameplayTimer.ball.setYPos(GameplayTimer.ball.getYPos() + GameplayTimer.normalizedDirectionY * this.throwSpeed);
		
		//bouncing mechanics
		if (GameplayTimer.ball.getXPos() < this.ballBoundsMarginX-10 || GameplayTimer.ball.getXPos() > Gameplay.WINDOW_WIDTH-this.ballBoundsMarginX) {
			GameplayTimer.normalizedDirectionX *= -1;	//reverse x direction
		}
		if(GameplayTimer.ball.getYPos() < this.ballBoundsMarginY || GameplayTimer.ball.getYPos() > Gameplay.WINDOW_HEIGHT-this.ballBoundsMarginY) {
			GameplayTimer.normalizedDirectionY *= -1;	//reverse y direction
		}
	
	}

	private void renderSprites() {
		gc.clearRect(0, 0, Gameplay.WINDOW_WIDTH, Gameplay.WINDOW_HEIGHT);
		gc.drawImage(background, 0, 0, Gameplay.WINDOW_WIDTH,Gameplay.WINDOW_HEIGHT);
		GameplayTimer.thrower1.render(this.gc);
		GameplayTimer.thrower2.render(this.gc);
		GameplayTimer.taya.render(this.gc);
		GameplayTimer.ball.render(gc);
	}
	
	
	
	//This section is for handling multiple inputs
	private static void followActiveThrower() {
		if(!GameplayTimer.isBallMoving) {
			if(GameplayTimer.isThrower1Active) {
				GameplayTimer.ball.setXPos(GameplayTimer.thrower1.getXPos() + GameplayTimer.ballRadius);
				GameplayTimer.ball.setYPos(GameplayTimer.thrower1.getYPos() - (GameplayTimer.throwerSize/2));
			} else {
				GameplayTimer.ball.setXPos(GameplayTimer.thrower2.getXPos() + GameplayTimer.ballRadius);
				GameplayTimer.ball.setYPos(GameplayTimer.thrower2.getYPos() + GameplayTimer.throwerSize);
			}
			
		}
	}

}
