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
	private Image tayaHarap = new Image("images/tayaharap.png");
	private Image tayaLikod = new Image("images/tayalikod.png");
	private Image tayaLeft = new Image("images/tayaleft.png");
	private Image tayaRight = new Image("images/tayaright.png");
	
	public GameplayTimer(GraphicsContext gc, Scene scene, Stage stage) {
		this.gc = gc;
		this.scene = scene;
		this.stage = stage;
		this.prepareActionHandlers();
		
		//initializing throwers
		this.throwerStartingX = Gameplay.WINDOW_WIDTH/2 - this.characterSize/2;
		GameplayTimer.thrower1 = new Thrower(this.throwerStartingX, 575, this.characterSize, this.throwerImage1,10,true);
		GameplayTimer.thrower2 = new Thrower(this.throwerStartingX, 60, this.characterSize, this.throwerImage2,10,false);
		
		//initializing ball
		GameplayTimer.ball = new Ball(GameplayTimer.thrower1.getXPos() + 30, GameplayTimer.thrower1.getYPos()-(this.characterSize/2), 30, this.ballImage,10);
		
		//initializing taya
		GameplayTimer.taya = new Taya(200,350,this.characterSize,this.characterSize,this.tayaHarap,9);
		GameplayTimer.taya.initDirectionalImages(this.tayaHarap, this.tayaLikod, this.tayaLeft, this.tayaRight);
	}


	@Override
	public void handle(long currentNanoTime) {
        this.moveSprites();
        this.renderSprites();
        
        //when taya was hit by ball
		if(GameplayTimer.ball.collidesWith(GameplayTimer.taya)) {
			this.stop();
			Gameplay.setGameOver(stage);
			GameplayTimer.ball.setMoving(false);
		}
	}

	private void prepareActionHandlers() {
    	
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
	
	private void moveSprites() {
		this.moveTaya();
		this.moveBall();
		this.moveThrowers();
	}
	
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
		if (GameplayTimer.ball.getXPos() < this.ballBoundsMarginX-10 || GameplayTimer.ball.getXPos() > Gameplay.WINDOW_WIDTH-this.ballBoundsMarginX) {
			GameplayTimer.ball.setNormalizedVectorX(GameplayTimer.ball.getNormalizedVectorX() * -1);	//reverse x direction
		}
		if(GameplayTimer.ball.getYPos() < this.ballBoundsMarginY || GameplayTimer.ball.getYPos() > Gameplay.WINDOW_HEIGHT-this.ballBoundsMarginY) {
			GameplayTimer.ball.setNormalizedVectorY(GameplayTimer.ball.getNormalizedVectorY() * -1);	//reverse y direction
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

}
