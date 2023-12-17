/*************************************************************************************************************************
 *
 * Taya
 * The one who dodges the ball
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/

package gameplay;

import javafx.scene.image.Image;

public class Taya extends Sprite{
	//images for each state
	private Image harap;
	private Image likod;
	private Image left;
	private Image right;
	private Image gotHit;
	
	//speed
	private double speed;
	
	//constructor
	public Taya(double xPos, double yPos, double width, double height, Image image, double speed) {
		super(xPos,yPos,width,height,image,true);
		this.speed = speed;
		this.isShorter = true;
	}
	
	//initialize images for different directions
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
