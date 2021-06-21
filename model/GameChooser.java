package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class GameChooser extends VBox {
	
	private ImageView circleImage;
	private ImageView playImage;
	
	private String circleNotChosen = "view/resources/gamechooser/grey_boxNotTicked.png";
	private String circleChosen = "view/resources/gamechooser/grey_boxTick.png";
	
	private GAMES game;
	
	private boolean isCircleChosen;
	
	/**
	 * Initializes game chooser using pictures of a circle button 
	 * that is chosen and not chosen
	 * @param game
	 * @author mkchama
	 */
	
	public GameChooser(GAMES game) {
		circleImage = new ImageView(circleNotChosen);
		playImage = new ImageView(game.getUrl());
		this.game = game;
		isCircleChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.getChildren().add(circleImage);
		this.getChildren().add(playImage);
		
		
		}
	
	//Returns game from GAMES
	
	public GAMES getGames() {
		return game;
	}
	
	//Returns if the circle image is chosen
	
	public boolean getIsCircleChosen() {
		return isCircleChosen;
	}
	
	
	/**
	 * Method to set the chosen circle
	 * @param isCircleChosen
	 * @author mkchama
	 */
	
	public void setIsCircleChosen(boolean isCircleChosen) {
		this.isCircleChosen = isCircleChosen;
		String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
		circleImage.setImage(new Image(imageToSet));
	}
	
}
