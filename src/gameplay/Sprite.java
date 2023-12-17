/*************************************************************************************************************************
 *
 * Sprite
 * The base class for scene objects that needs collision detection
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/


package gameplay;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
	//props
	protected Image img;
	protected double xPos, yPos, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;
	protected boolean isShorter = false;
	
	//constructor
    public Sprite(double xPos, double yPos, double width, double height, Image image){
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.setImage(image);
		this.visible = true;
	}
    
    //constructor with shorter property (for smaller collision box)
    public Sprite(double xPos, double yPos, double width, double height, Image image, boolean isShorter){
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.setImage(image);
		this.visible = true;
		this.isShorter = isShorter;
	}
    
    //get bounds
	private Rectangle2D getBounds(boolean isShorter){
		if(isShorter) {
			return new Rectangle2D(this.xPos + this.width/4, this.yPos+ this.height/4, this.width/2, this.height/2);
		}
		return new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}
	
	//check collision
	protected boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds(true);
		Rectangle2D rectangle2 = rect2.getBounds(this.isShorter);

		return rectangle1.intersects(rectangle2);
	}
	
	//render image
	public void render(GraphicsContext gc){
        gc.drawImage( this.img, this.xPos, this.yPos, this.width, this.height );
    }


	public Image getImage(){
		return this.img;
	}

	public void setImage(Image img) {
		this.img = img;
	}
	
	public double getXPos(){
		return this.xPos;
	}

	public double getYPos(){
		return this.yPos;
	}
	
	public void setXPos(double x){
		this.xPos = x;
	}

	public void setYPos(double y){
		this.yPos = y;
	}


	public void setDX(double d){
		this.dx = d;
	}
	
	public double getDX() {
		return this.dx;
	}
	
	public void setDY(double val){
		this.dy = val;
	}
	
	public double getDY() {
		return this.dy;
	}

	public boolean isVisible(){
		return visible;
	}
	
	public void vanish(){
		this.visible = false;
	}
}
