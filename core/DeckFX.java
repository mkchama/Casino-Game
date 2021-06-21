package core;

import java.util.Arrays;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DeckFX extends Deck {
	
	private CardFX[] cardDeck;

	public DeckFX() {
		super();
		this.createDeck(5);
		this.shuffleDeck();
		CardFX[] cardDeck = new CardFX[52];
		this.createDeckFX(cardDeck);
		this.cardDeck = cardDeck;
	}
	
	public CardFX[] getCardDeck() {
		return cardDeck;
		
	}
	
	public void createDeckFX(CardFX[] cardDeck) {
		Arrays.fill(cardDeck, null);
		for (int i = 0; i < 51; i++) {
			CardFX card = new CardFX(super.getCard(i).getSuit(), super.getCard(i).getRank());
			card.getCardStack().setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					card.getCardStack().setScaleX(1.05);
					card.getCardStack().setScaleY(1.05);
					card.getCardStack().setEffect(new DropShadow());
				}
				
			});
			card.getCardStack().setOnMouseExited(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					card.getCardStack().setScaleX(1);
					card.getCardStack().setScaleY(1);	
					card.getCardStack().setEffect(null);
				}
				
			});
			cardDeck[i] = card;
		}
	}
	
	public CardFX getCardFX(int cardsDrawn) {
		return (this.getCardDeck()[cardsDrawn]);
	}
	
	public void showCard(Pane mainPane, int x, int y, int cardsDrawn) {
		this.getCardDeck()[cardsDrawn].getCardStack().setLayoutX(x);
		this.getCardDeck()[cardsDrawn].getCardStack().setLayoutY(y);
		mainPane.getChildren().add(this.getCardDeck()[cardsDrawn].getCardStack());
	}
	
	public void cardDrawn(int xTo, int yTo, int xFrom, int yFrom, int cardsDrawn, int handDrawn, Pane mainPane) {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(this.getCardDeck()[cardsDrawn].getCardStack());
		translate.setDuration(Duration.seconds(0.3));
		int x = -xTo + (handDrawn * 160);
		translate.setToY(yTo);
		translate.setToX(x);
		translate.play();
		cardsDrawn++;
		this.showCard(mainPane, xFrom, yFrom, cardsDrawn);
	}
}
