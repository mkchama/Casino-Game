package model;

public enum GAMES {
	
	WAR("view/resources/gamechooser/newwar.png"),
	BLACKJACK("view/resources/gamechooser/bjnew.png");

	
	private String urlGame;
	
	/**
	 * Converts the ENUM to a string
	 * @param urlGame
	 * @author mkchama
	 */
	
	private GAMES(String urlGame) {
		this.urlGame = urlGame;
	}
	
	//Get method for the URL
	
	public String getUrl() {
		return this.urlGame;
	}
}
