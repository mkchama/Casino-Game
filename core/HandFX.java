package core;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class HandFX extends Hand {

	private CardFX[] aHand;
	private Boolean isDealerHand;
	
	
	/**
	 * Creates a new HandFX that can hold half a deck.
	 * Takes in a boolean to determine if the player will be allowed to 
	 * flip the cards by clicking on them
	 * @param isDealer
	 * @author tyler.hartleb1
	 */
	
	public HandFX(Boolean isDealer) {
		super();
		this.aHand = new CardFX[26];
		this.isDealerHand = isDealer;
	}
	
	
	/**
	 * Takes in a pane and a Hand and removes the cards from the pane one at a time.
	 * It then calls the super to clear the hand.
	 * @param aPane
	 * @param aHand
	 * @author tyler.hartleb1
	 */
	
	public void clearHandFX(AnchorPane aPane, HandFX aHand) {
		for (int i = 0; i < aHand.handSize(); i++) {
			aPane.getChildren().remove( aHand.aHand[i].getCardStack() );
		}
		super.clearHand();
	}
	
	
	/**
	 * Takes in the number of cards drawn for the dealer, to be called if you want to flip 
	 * all the dealer cards at once
	 * @param cardsDrawn
	 * @author tyler.hartleb1
	 */
	
	public void flipAllDealer(int cardsDrawn) {
		for(int i = 0; i < cardsDrawn; i ++ ) {
			this.aHand[i].flip(this.aHand[i].getCardStack(), this.aHand[i].getFrontImage(), this.aHand[i].getBackImage());
		}
	}
	
	
	/**
	 * Animates the card from once area of the gui to another, if the hand is not a dealer hand 
	 * it will also flip the card. Calls the method showCard from class DeckFX.
	 * @param aHand
	 * @param aDeck
	 * @param xTo
	 * @param yTo
	 * @param xFrom
	 * @param yFrom
	 * @param cardsDrawn
	 * @param handDrawn
	 * @param mainPane
	 * @author tyler.hartleb1
	 */
	
	public void animateCard(CardFX[] aHand, DeckFX aDeck, int xTo, int yTo, int xFrom, int yFrom, int cardsDrawn, int handDrawn, Pane mainPane) {
		TranslateTransition translate = new TranslateTransition();
		CardFX card = this.aHand[handDrawn];
		translate.setNode(this.aHand[handDrawn].getCardStack());
		translate.setDuration(Duration.seconds(0.3));
		int x = -xTo + (handDrawn * 160);
		translate.setToY(yTo);
		translate.setToX(x);
		translate.play();
		if (getIsDealer() == false) {
			card.flip(card.getCardStack(), card.getFrontImage(), card.getBackImage());
		}
		aDeck.showCard(mainPane, xFrom, yFrom, cardsDrawn);
	}
	
	
	/**
	 * Draws a card from the super, then if it is from a player hand it will allow the card to be flip-able.
	 * Calls animateCard
	 * @param aDeck
	 * @param xTo
	 * @param yTo
	 * @param xFrom
	 * @param yFrom
	 * @param cardsDrawn
	 * @param handDrawn
	 * @param mainPane
	 * @author tyler.hartleb1
	 */
	
	public void drawCard(DeckFX aDeck, int xTo, int yTo, int xFrom, int yFrom, int cardsDrawn, int handDrawn, Pane mainPane) {
		super.drawCard(aDeck);
		
		this.aHand[handDrawn] = aDeck.getCardDeck()[cardsDrawn];
		CardFX card = this.aHand[handDrawn];
		if (getIsDealer() == false) {
			card.getCardStack().setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					card.flip(card.getCardStack(), card.getFrontImage(), card.getBackImage());	
				}
				
			});
		}
		cardsDrawn++;
		this.animateCard(this.aHand, aDeck, xTo, yTo, xFrom, yFrom, cardsDrawn, handDrawn, mainPane);
	}
	
	
	public Boolean getIsDealer() {
		return isDealerHand;
	}
}
