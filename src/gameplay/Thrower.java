package gameplay;

import javafx.scene.image.Image;

public class Thrower extends Sprite{
	
	private double speed;
	private boolean isHoldingBall;

	public Thrower(double xPos, double yPos, double size, Image image,double speed,boolean isHoldingBall) {
		super(xPos,yPos,size,size,image);
		this.setSpeed(speed);
		this.setHoldingBall(isHoldingBall);
	}

	public boolean isHoldingBall() {
		return isHoldingBall;
	}

	public void setHoldingBall(boolean isHoldingBall) {
		this.isHoldingBall = isHoldingBall;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
