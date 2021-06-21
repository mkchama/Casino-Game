package core;

import java.io.IOException;
import java.util.Collections;

import javafx.animation.RotateTransition;
import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CardFX extends Card {

	private StackPane card;
	private ImageView frontImage;
	private ImageView backImage;
	private Boolean isFlipped = false;
	

	
	public CardFX(Suit suit, Rank rank)  {
		super(suit, rank);
		String firstPart = new String (suit.toString().toLowerCase());
		String secondPart = new String (rank.toString().toLowerCase());
		String complete = new String("/cards/" + firstPart + "_" + secondPart + ".png");
		try {
			this.frontImage = new ImageView(complete);
			this.backImage = new ImageView("/cards/blue_back.png");
		}
		catch(Exception e) {
			System.out.println("oh no");
		}
		this.card = new StackPane();
		this.card.getChildren().addAll(frontImage,backImage);
	}

	public StackPane getCardStack() {
		return card;
	}
	
	public ImageView getFrontImage() {
		return frontImage;
	}
	
	public ImageView getBackImage() {
		return backImage;
	}
	
	public void flip(StackPane cardFX, ImageView frontFace, ImageView backFace) {
		RotateTransition btnRotate = new RotateTransition(Duration.seconds(0.5), cardFX);
		RotateTransition btnRotate2 = new RotateTransition(Duration.seconds(0.5), cardFX);
		btnRotate.setByAngle(90);
		btnRotate.setAxis(Rotate.Y_AXIS);
		btnRotate2.setFromAngle(90);
		btnRotate2.setToAngle(180);
		btnRotate2.setAxis(Rotate.Y_AXIS);
		btnRotate.setRate(1);
		btnRotate.setCycleCount(1);
		btnRotate.play();
		
		if (getIsFlipped() == false ) {
			btnRotate.statusProperty().addListener(new ChangeListener<Status>() {
				
				@Override
				public void changed(ObservableValue<? extends Status> arg0, Status arg1, Status arg2) {
					if (arg2 == Status.STOPPED) {
						frontFace.setScaleZ(-1);
						frontFace.setScaleX(1);
						frontFace.setScaleY(-1);
						ObservableList<Node> workingCollection = FXCollections.observableArrayList(cardFX.getChildren());
				        Collections.swap(workingCollection, 0, 1);
				        cardFX.getChildren().setAll(workingCollection);
						btnRotate2.setNode(cardFX);
						btnRotate2.play();
						setIsFlipped(true);
						
					}
				
				}
			});
		}
		else {
			btnRotate.statusProperty().addListener(new ChangeListener<Status>() {
				
				@Override
				public void changed(ObservableValue<? extends Status> arg0, Status arg1, Status arg2) {
					if (arg2 == Status.STOPPED) {
						ObservableList<Node> workingCollection = FXCollections.observableArrayList(cardFX.getChildren());
				        Collections.swap(workingCollection, 0, 1);
				        cardFX.getChildren().setAll(workingCollection);
						btnRotate2.setNode(cardFX);	
						btnRotate2.play();
						setIsFlipped(false);
					}
				
				}
			});
		}
	}
	
	public Boolean getIsFlipped() {
		return isFlipped;
	}
	
	public void setIsFlipped(Boolean bool) {
		this.isFlipped = bool;
	}

}
