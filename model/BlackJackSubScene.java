package model;



import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class BlackJackSubScene extends SubScene {

	
	private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "model/resources/grey_panel.png";
	
	
	private boolean isHidden;
	
	
	
	
	/**
	 * Displays the sub scenes in the main menu
	 * @author mkchama
	 */
	
	public BlackJackSubScene() {
		super(new AnchorPane(), 600, 475);
		prefWidth(600);
		prefHeight(475);
		
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,600,475,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
	
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		
		isHidden = true;
		
		setLayoutX(1048);
		setLayoutY(200);
		
		
	}
	
	
	/**
	 * Animates the movement of the sub scenes in the main menu
	 * @author mkchama
	 */
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(isHidden) {
		
		transition.setToX(-676);
		isHidden = false;
		} 
		else {
			transition.setToX(0);
			isHidden = true;
		}
		
		transition.play();
		
	}
	
	//Get method for the pane
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
		
	}
	

}
