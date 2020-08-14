package model;

public enum SHIP {
	FIGHTER_1("view/resources/shipchooser/playerShip1.png","view/resources/playerLife1.png"),
	FIGHTER_2("view/resources/shipchooser/playerShip2.png","view/resources/playerLife2.png"),
	FIGHTER_3("view/resources/shipchooser/playerShip3.png","view/resources/playerLife3.png"),
	FIGHTER_4("view/resources/shipchooser/playerShip4.png","view/resources/playerLife4.png");
	
	private String urlShip;
	private String urlLife;
	
	private SHIP(String urlShip, String urlLife) {
		this.urlShip = urlShip;
		this.urlLife = urlLife;
	}
	
	public String getURL() {
		return this.urlShip;
	}
	
	public String getUrlLife() {
		return this.urlLife;
	}
}
