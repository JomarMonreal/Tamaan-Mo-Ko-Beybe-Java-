package gameplay;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
	protected Image img;
	protected double xPos, yPos, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

    public Sprite(double xPos, double yPos, double width, double height, Image image){
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.loadImage(image);
		this.visible = true;
	}

	private Rectangle2D getBounds(){
		return new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}

	protected boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}

	protected void loadImage(Image image){
		try{
			this.img = image;
		} catch(Exception e)	{
			e.printStackTrace();
		}
	}
	
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
