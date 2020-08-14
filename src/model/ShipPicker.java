package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox{
	
	private ImageView circleImage, shipImage;
	
	private String circleNotChoosen = "view/resources/shipchooser/yellow_circle.png";
	private String circleChoosen = "view/resources/shipchooser/yellow_circle_checked.png";
	
	private SHIP ship;
	
	private boolean isCircleChoosen;
	
	public ShipPicker(SHIP ship) {
		this.circleImage = new ImageView(this.circleNotChoosen);
		this.shipImage = new ImageView(ship.getURL());
		this.ship = ship;
		this.isCircleChoosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(shipImage);
	}
	
	public SHIP getShip() {
		return this.ship;
	}
	
	public boolean isCircleChoosen() {
		return this.isCircleChoosen;
	}
	
	public void setIsCircleChoosen(boolean isCircleChoosen) {
		this.isCircleChoosen = isCircleChoosen;
		String imageToSet = this.isCircleChoosen ? this.circleChoosen : this.circleNotChoosen;
		this.circleImage.setImage(new Image(imageToSet));
	}
}
