/*************************************************************************************************************************
 *
 * Ball 
 * The object being thrown by the thrower and must be dodged by the dodger
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/


package gameplay;

import javafx.scene.image.Image;

public class Ball extends Sprite{
	//normalized vector components for movement
	private double normalizedVectorX = 0;
	private double normalizedVectorY = 0;
	
	//common props
	private boolean isMoving = false;
	private double radius;
	private double speed = 8;
	
	//constructor
	public Ball(double xPos, double yPos, double radius,Image img,double speed) {
		super(xPos, yPos, radius, radius,img);
		this.radius = radius;
		this.setSpeed(speed);
	}
	
	//set normalized vector based on mouse position
	public void setNormalizedVector(double mousePosX, double mousePosY) {
	 
		// find direction of ball thrown when mouse clicked  
		double directionX = mousePosX - this.getXPos();
		double directionY = mousePosY - this.getYPos();
		
		// get magnitude to be used for computing new direction for x and y
		double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
		
		// get unit vector for x and y
		this.setNormalizedVectorX(directionX / magnitude);
		this.setNormalizedVectorY(directionY / magnitude) ;
        
        this.setMoving(true);
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public double getNormalizedVectorY() {
		return normalizedVectorY;
	}

	public void setNormalizedVectorY(double normalizedVectorY) {
		this.normalizedVectorY = normalizedVectorY;
	}

	public double getNormalizedVectorX() {
		return normalizedVectorX;
	}

	public void setNormalizedVectorX(double normalizedVectorX) {
		this.normalizedVectorX = normalizedVectorX;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
