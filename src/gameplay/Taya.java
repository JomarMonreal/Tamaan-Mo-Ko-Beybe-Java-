package gameplay;

import javafx.scene.image.Image;

public class Taya extends Sprite{
	private Image harap;
	private Image likod;
	private Image left;
	private Image right;
	private Image gotHit;
	
	private double speed;
	
	public Taya(double xPos, double yPos, double width, double height, Image image, double speed) {
		super(xPos,yPos,width,height,image);
		this.speed = speed;
		this.isShorter = true;
	}
	
	public void initDirectionalImages(Image harap,Image likod,Image left, Image right,Image gotHit) {
		this.harap = harap;
		this.likod = likod;
		this.left = left;
		this.right = right;
		this.gotHit = gotHit;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public Image getHarap() {
		return harap;
	}

	public Image getLikod() {
		return likod;
	}

	public Image getLeft() {
		return left;
	}

	public Image getRight() {
		return right;
	}

	public Image getGotHit() {
		return gotHit;
	}

}
