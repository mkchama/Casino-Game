package core;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class ChipHand {

	private Chips[] aHand;
	private Boolean isPlayer;
	private int chipsAdded;
	
	public ChipHand(boolean bool) {
		this.aHand = new Chips[100];
		this.isPlayer = bool;
		this.chipsAdded = 0;
	}
	
	
	/**
	 * clears the hand of any chips
	 */
	
	public void clearHand() {
		this.aHand = new Chips[aHand.length];
	}

	
	/**
	 * adds a chip to the hand in the index chippulled
	 * @param chipPulled
	 * @param aChip
	 */
	
	public void addChips(int chipPulled, Chips aChip) {
		this.aHand[chipPulled] = aChip;
		this.chipsAdded++;
	}
	
	
	
	/**
	 * Draws a chip and places it on the anchor pane in the x and y value given
	 * @param aChip
	 * @param yTo
	 * @param mainPane
	 * @param aStack
	 * @param x
	 * @param y
	 */
	public void drawChip(Chips aChip, int yTo, AnchorPane mainPane, StackPane aStack, int x, int y) {
		this.addChips(this.getChipsAdded(), aChip);
		aChip.getChipView().setLayoutX(x);
		aChip.getChipView().setLayoutY(y);
		mainPane.getChildren().add(aChip.getChipView());
		animateChips(yTo, aChip);
		aStack.getChildren().add(aChip.getChipView());
	}
	
	
	/**
	 * Animates the chip from the dealer hand to the player hand
	 * @param yTo
	 * @param aChip
	 */
	
	public void animateChips(int yTo, Chips aChip) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode( aChip.getChipView() );
		translate.setDuration(Duration.seconds(0.3));
		translate.setFromY(yTo + 74);
		translate.setToY(yTo);
		translate.play();
	}
	
	
	/**
	 * Returns the chipHand
	 * @return
	 */
	
	public Chips[] getHand() {
		return aHand;
	}
	
	
	/**
	 * returns the int chipsAdded
	 * @return
	 */
	
	public int getChipsAdded( ) {
		return chipsAdded;
	}
}
