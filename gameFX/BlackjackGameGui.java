package gameFX;

import BlackJack.Blackjack;
import core.HandFX;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BlackJackButton;
import model.infoLabel;
import view.ViewManager;

public class BlackjackGameGui extends GameFX {
	
	private BlackJackButton bet, hit, stand, x2, clear, surrender, returnToMain, nextRound, restart;
	private int playerCardsDrawn, dealerCardsDrawn;
	private infoLabel betAmount, maxBet;
	private HandFX playerHand, dealerHand;
	private HBox buttonBoxLogic;
	private VBox continueBoxButton;
	
	
	public BlackjackGameGui() {
		super();
		this.playerCardsDrawn = 0;
		this.dealerCardsDrawn = 0;
		this.playerHand = new HandFX(false);
		this.dealerHand = new HandFX(true);
		
	}

	@Override
	public void initGame(Stage menuStage) {
		super.initLabel();
		this.initButtons();
		super.initChipsDealer();
		super.initChipsPlayer();
		super.initDeck();
			
		super.getMainStage().setScene(super.getScene());
		createBackground();
		menuStage = super.getMainStage();
		menuStage.getIcons().add(new Image("FX/resources/icon1.png"));
		menuStage.setResizable(false);
		menuStage.show();
		
	}

	@Override
	public void initButtons() {
		bet = new BlackJackButton("Bet", 16, 49, 190);
		bet.setOnAction(buttonActions());
		
		hit = new BlackJackButton("Hit", 16, 49, 190);
		hit.setOnAction(buttonActions());
		
		stand = new BlackJackButton("Stand", 16, 49, 190);
		stand.setOnAction(buttonActions());
		
		x2 = new BlackJackButton("Double", 16, 49, 190);
		x2.setOnAction(buttonActions());
		
		clear = new BlackJackButton("Clear", 16, 49, 190);
		clear.setOnAction(buttonActions());
		
		surrender = new BlackJackButton("Surrender", 16, 49, 190);
		surrender.setOnAction(buttonActions());
		
		returnToMain = new BlackJackButton("Menu", 23, 49, 190);
		returnToMain.setOnAction(buttonActions());
		
		restart = new BlackJackButton("New Game", 23, 49, 190);
		restart.setOnAction(buttonActions());
		
		nextRound = new BlackJackButton("Continue", 23, 49, 190);
		nextRound.setOnAction(buttonActions());
		
		betAmount = new infoLabel("Bet:" + super.getBet(), 190, 49, 15);
		betAmount.setLayoutX(10);
		betAmount.setLayoutY(640);	
		
		maxBet = new infoLabel("Max Bet: 100", 190, 49, 12);
		maxBet.setLayoutX(10);
		maxBet.setLayoutY(591);	
		
		buttonBoxLogic = new HBox();
		continueBoxButton = new VBox();
		
		continueBoxButton.setLayoutX(10);
		continueBoxButton.setLayoutY(640);
		
		buttonBoxLogic.getChildren().add(bet);
		buttonBoxLogic.getChildren().add(clear);
		buttonBoxLogic.setLayoutY(640);
		buttonBoxLogic.setLayoutX(580);
		
		super.getButtonBoxSettings().getChildren().add(returnToMain);
		super.getButtonBoxSettings().getChildren().add(restart);
		
		super.getMainPane().getChildren().addAll(buttonBoxLogic,continueBoxButton);
	}

	@Override
	public void animateUpdateRound() {
		super.getRoundTurn().setText("Deck Reset");
		Timeline animateLabel = new Timeline(
				new KeyFrame(Duration.seconds(4)));
		animateLabel.setCycleCount(1);
		animateLabel.setOnFinished(event -> setRoundTurn());
		animateLabel.play();
		
	}

	@Override
	public void clearTable() {
		setBetLabel(getBet());
		playerHand.clearHandFX(super.getMainPane(), playerHand);
		dealerHand.clearHandFX(super.getMainPane(), dealerHand);
		super.getButtonBoxSettings().getChildren().removeAll(super.getWinLose(), bet);
		continueBoxButton.getChildren().removeAll(nextRound);
		clearChips();
		
	}
	
	

	/**
	 * This method removes surrender from the button box
	 * @author tyler.hartleb1
	 */
	
	public void removeSurrender() {
		if (buttonBoxLogic.getChildren().contains(surrender) == true) {
			buttonBoxLogic.getChildren().remove(surrender);
		}
	}
	
	/**
	 * This method removes hit from the button box 
	 * @author mkchama
	 */
	
	public void removeHit() {
		if (buttonBoxLogic.getChildren().contains(hit) == true) {
			buttonBoxLogic.getChildren().remove(hit);
		}
	}
	
	
	// These methods below are for handling the rounds and turns
	
	
		/**
		 * This method starts the first round
		 * @author tyler.hartleb1
		 */
		
		public void roundStart() {
			super.getButtonBoxSettings().getChildren().remove(restart);
			setRoundStart(true);
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			buttonBoxLogic.getChildren().clear();
			continueBoxButton.getChildren().clear();
			for (int i = 0; i < 2; i ++) {
				playerDraw();
			}
			for (int i = 0; i < 2; i ++) {
				dealerDraw();
			}	
			
			buttonBoxLogic.getChildren().addAll(hit, x2, stand, surrender);
		}
		
		
		/**
		 * This method ends the round and sets the labels to who won or lost, takes in the win condition from
		 * the winFunction method
		 * @param winCond
		 * @author tyler.hartleb1
		 */
		
		public void roundEnd(int winCond) {	
			System.out.println(playerHand.handValue(playerHand));
			setRoundStart(false);
			buttonBoxLogic.getChildren().clear();
			continueBoxButton.getChildren().clear();
			dealerHand.flipAllDealer(dealerCardsDrawn);
			super.getButtonBoxSettings().getChildren().add(restart);
			if (winCond == 0) {
				updateBalance( (  - getBet() ) );
				
				super.getWinLose().setText("LOSS: -" + getBet());
				
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			else if (winCond == 1) {
				updateBalance( getBet() );
				
				super.getWinLose().setText("WIN: +" + getBet());
				
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			else if (winCond == 2) {
				super.getWinLose().setText("TIE: 0");
			
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
				
			}
			else if (winCond == 4) {
				updateBalance( -(getBet() / 2) );
				
				super.getWinLose().setText("Surrendered - " + getBet()/2);
		
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			setBetLabel(getBet());
		}
		
		
		/**
		 * This method starts a newRound
		 * @author tyler.hartleb1
		 */
		public void newRound() {
			initChipsPlayer();
			if (super.getRound() == 5) {
				this.initDeck();
				setRound(1);
				animateUpdateRound();
			}
			else {
				super.roundPlus();
				setRoundTurn();
			}
			super.setRoundOver(false);;
			buttonBoxLogic.getChildren().clear();
			continueBoxButton.getChildren().clear();
			buttonBoxLogic.getChildren().add(bet);
			buttonBoxLogic.getChildren().add(clear);
		}
		
		
		/**
		 * This method ends the turn after an action has been entered
		 * @author tyler.hartleb1
		 */
		
		public void turnOver() {
			super.turnPlus();
			setRoundTurn();
			removeSurrender();
			//removeHit();
			//removeStand();
			//removeXTwo();
			winFunction(false);
		}
		
	
		// These methods implement the button actions
		
		
		/**
		 * This method draws a player card and dealerCard and ends the turn
		 * @author tyler.hartleb1
		 */
		
		public void hit() {
			playerDraw();
			dealerDraw();
			if (getPlayerHand().handValue(getPlayerHand()) > 21) {
				setRoundOver(true);
			}
			turnOver();	
		}
		

		/**
		 * This method doubles the bet, and runs the stand method after drawing a player card
		 * @author tyler.hartleb1
		 */
		
		public void xTwo() {
			if ( !( (getBet()*2 ) > super.getBalance()) ) {
				playerDraw();
				addBet(getBet());
				setBetLabel(getBet());
				stand();
			}
			else {
				animateUpdateLabel(getBet() * 2);
			}
			
			
		}
		
		
		/**
		 * This method ends the turn, and draws dealer cards while the dealer hand value is below 17
		 * @author tyler.hartleb1
		 */
		
		public void stand() {
			while ( (dealerHand.handValue(dealerHand.getHand() ) < 17 ) ) {
				dealerDraw();
			}
			setRoundOver(true);
			turnOver();
		}
		
		public void newGame() {
			clearBalance();
			updateBalance(500);
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			buttonBoxLogic.getChildren().clear();
			zeroEndRound();
			clearBet();
			clearTable();
			setRound(5);
			
		}
		
		
		/**
		 * This method surrenders and sends true to the winFunction
		 * @author tyler.hartleb1
		 */
		
		public void surrender() {
			winFunction(true);
		}
		
	
		// These methods implement game logic
		
		
		/**
		 * Draws a player card from the deck
		 * @author tyler.hartleb1
		 */
		
		public void playerDraw() {
			playerHand.drawCard(super.getDeck(), 990, 346, 1200, 10, super.getCards(), this.playerCardsDrawn, super.getMainPane());
			super.cardsDrawnPlus();
			this.playerCardsDrawn++;
		}
		
		
		/**
		 * This method draws a dealer card if the hand value is below 17
		 * @author tyler.hartleb1
		 */
		
		public void dealerDraw() {
			if (dealerHand.handValue(dealerHand.getHand()) < 17) {
				dealerHand.drawCard(super.getDeck(), 990, 46, 1200, 10, super.getCards(), this.dealerCardsDrawn, super.getMainPane());
				super.cardsDrawnPlus();
				this.dealerCardsDrawn++;
			}
		}
		
		
		/**
		 * This method runs the Blackjack class winCondition which returns a winCondtion int,
		 * This method will run the round end function if it needs to
		 * @param surrender
		 * @author tyler.hartleb1
		 */
		
		public void winFunction(Boolean surrender) {
			if (surrender == true) {
				int winCond = 4;
				roundEnd(winCond);
			}
			else {
				int winCond = Blackjack.winCondition(playerHand, dealerHand);
				if ( (winCond == 0) || (winCond == 1) || (winCond == 2) ) {
					if (super.getRoundOver() == true) {
						roundEnd(winCond);
					}
				}
			}
		}

	@Override
	public void zeroTotalDrawn() {
		super.zeroCardsDrawn();;
		this.playerHand.zeroCardCount();
		this.dealerHand.zeroCardCount();
		
	}

	@Override
	public void zeroDrawn() {
		this.playerCardsDrawn = 0;
		this.dealerCardsDrawn = 0;
		super.zeroChipsDrawn();
		
	}
	
	
	public HandFX getPlayerHand() {
		return playerHand;
	}
	
	
	
	
	// These methods handle Action and Button Events
	
	
		/**
		 * This method returns an EventHandler in order to set actions to the buttons
		 * in this program
		 * @return
		 * @author tyler.hartleb1
		 */
		
		public EventHandler<ActionEvent> buttonActions() {
			EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent event) {
					if ( event.getSource() == returnToMain) {
						getMainStage().hide();
						saveFile();
						ViewManager manager = new ViewManager();
						Stage primaryStage = manager.getMainStage();
						primaryStage.show();
					}
					
					if (event.getSource() == nextRound) {
						zeroEndRound();
						clearTable();
						newRound();
						saveFile();
					}
					
					if ( event.getSource() == bet ) {
						roundStart();
					}
					
					if ( event.getSource() == hit ) {
						hit();
					}
					if (event.getSource() == restart ) {
						newGame();
						newRound();
						saveFile();

					}
					
					if ( event.getSource() == x2 ) {	
						xTwo();	
					}
				
					if ( event.getSource() == stand ) {
						stand();
					}
					if ( event.getSource() == clear ) {
						clearBet();
						
					}
					
					if ( event.getSource() == surrender ) {
						surrender();
					}
				}
			};
			return handler;
		}

}
