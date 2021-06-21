package gameFX;

import java.io.BufferedReader;
import java.io.FileReader;

import War.War;
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

public class WarGameGui extends GameFX {
	
	private BlackJackButton cont, bet, clear, war, surrender, returnToMain, nextRound, restart;
	private int playerCardsDrawn, tableCardsDrawn, dealerCardsDrawn;
	private infoLabel betAmount, maxBet;
	private HBox buttonBoxLogic;
	private VBox continueBoxButton;
	private HandFX playerHand, dealerHand, tableHand;
	
	public WarGameGui() {
		super();
		this.tableCardsDrawn = 0;
		this.playerCardsDrawn = 0;
		this.dealerCardsDrawn = 0;
		this.tableHand = new HandFX(true);
		this.playerHand = new HandFX(false);
		this.dealerHand = new HandFX(false);
			
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
	
	
	/**
	 * This method initializes the buttons
	 * @author mkchama
	 */


	@Override
	public void initButtons() {
		bet = new BlackJackButton("Bet", 16, 49, 190);
		bet.setOnAction(buttonActions());
		
		war = new BlackJackButton("War", 16, 49, 190);
		war.setOnAction(buttonActions());
		
		surrender = new BlackJackButton("Surrender", 16, 49, 190);
		surrender.setOnAction(buttonActions());
		
		clear = new BlackJackButton("Clear", 16, 49, 190);
		clear.setOnAction(buttonActions());
		
		returnToMain = new BlackJackButton("MENU", 23, 49, 190);
		returnToMain.setOnAction(buttonActions());
		
		restart = new BlackJackButton("NEW GAME", 23, 49, 190);
		restart.setOnAction(buttonActions());
		
		nextRound = new BlackJackButton("CONTINUE", 23, 49, 190);
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
		
		super.getButtonBoxSettings().getChildren().addAll(returnToMain, restart);
		super.getMainPane().getChildren().addAll(buttonBoxLogic,continueBoxButton);
		
	}
    
    /**
	 * This method animates the update of the rounds
	 * @author mkchama
	 */
    
	@Override
	public void animateUpdateRound() {
		super.getRoundTurn().setText("Deck Reset");
		Timeline animateLabel = new Timeline(
				new KeyFrame(Duration.seconds(4)));
		animateLabel.setCycleCount(1);
		animateLabel.setOnFinished(event -> setRoundTurn());
		animateLabel.play();
	}
    
    /**
	 * This method clears the table
	 * @author mkchama
	 */
    
	@Override
	public void clearTable() {
		setBetLabel(getBet());
		tableHand.clearHandFX(super.getMainPane(), tableHand);
		dealerHand.clearHandFX(super.getMainPane(), dealerHand);
		playerHand.clearHandFX(super.getMainPane(), playerHand);
		super.getButtonBoxSettings().getChildren().removeAll(super.getWinLose());
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
	
	
	// These methods below are for handling the rounds and turns
	
	/**
	 * This method starts a new game when the balance has reached 0
	 * @author mkchama
	 */
		
		public void newGame() {
			clearBalance();
			updateBalance(500);
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			buttonBoxLogic.getChildren().clear();
			
		}
		
		/**
		 * This method starts the first round which will draw both 
		 * the players card and dealers card. They are then compared to see
		 * if they are of equal value to check if the player must go to War.
		 * @author mkchama
		 */
		
		public void roundStart() {
			super.setRoundStart(true);
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			buttonBoxLogic.getChildren().clear();
			continueBoxButton.getChildren().clear();
			playerDraw();
			dealerDraw();
			bet();
		}
		
		
		/**
		 * This method ends the round and sets the labels to who won or lost, takes in the win condition from
		 * the winFunction method
		 * @param winCond
		 * @author mkchama
		 */
		
		public void roundEnd(int winCond) {	
			setRoundStart(false);
			buttonBoxLogic.getChildren().clear();
			continueBoxButton.getChildren().clear();
			super.getButtonBoxSettings().getChildren().add(restart);
			
			if (winCond == 0) {
				updateBalance( ( getBet() ) );
				
				super.getWinLose().setText("WIN: +" + getBet());
				
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			else if (winCond == 1) {
				updateBalance( - getBet() );
				
				super.getWinLose().setText("LOSS: -" + getBet());
				
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			
			else if (winCond == 4) {
				updateBalance( - (getBet()));
				
				super.getWinLose().setText("Surrendered -" + getBet());
		
				super.getButtonBoxSettings().getChildren().addAll(super.getWinLose());
				continueBoxButton.getChildren().addAll(nextRound);
			}
			
			super.getBalanceLabel().setText("Balance: " + super.getBalance());
			setBetLabel(getBet());
		}
		
		
		/**
		 * This method starts a newRound and resets the deck once the 
		 * round has reached 10
		 * @author mkchama
		 */
		
		public void newRound() {
			initChipsPlayer();
			if (getRound() == 10) {
				this.initDeck();
				setRound(1);
				animateUpdateRound();
			}
			else {
				super.roundPlus();
				setRoundTurn();
			}
			super.setRoundOver(false);
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
			winFunction(false);
		}
		
		
		
		// These methods implement the button actions
		
		
		/**
		 * This method is called when the player and dealers cards are equal
		 * then presents the user with the choice to either go to war or surrender 
		 * @author mkchama
		 */
		
		public void war() {
			buttonBoxLogic.getChildren().clear();
			buttonBoxLogic.getChildren().addAll(war, surrender);
		}
		
		/**
		 * This method is called once war is selected and checks if the player
		 * has a sufficient balance to do so (x2 bet). It goes to war and this will 
		 * burn three cards that are given to the table hand(face down)
		 * It also checks the winning condition based on the result. 
		 * @author mkchama
		 */
		
		public void goToWar() {
			if (( (getBet()*2 ) <= super.getBalance())) {
				addBet(getBet());
				setBetLabel(getBet());
				tableBurn();
				playerDraw();
				dealerDraw();
				bet();
			}
			
		}
		
		/**
		 * This method is called if the player and dealer hands are not equal
		 * and checks the winning condition.
		 * @author mkchama
		 */
		
		public void bet() {
			super.getButtonBoxSettings().getChildren().remove(restart);
			int winCond = War.winConditions(playerHand, dealerHand);
			if ( (winCond == 0) || (winCond == 1)) {
				setRoundOver(true);
				turnOver();
			}
			else {
				war();
			}
		}

		/**
		 * This method surrenders and sends true to the winFunction
		 * @author mkchama
		 */
		
		public void surrender() {
			winFunction(true);
		}
		
		/**
		 * This method draws three cards onto the table that will be burned
		 * @author mkchama
		 */
		
		public void tableBurn() {
			tableDraw();
			tableDraw();
			tableDraw();
			
		}
		
		// These methods implement game logic
		
		/**
		 * Draws a table card from the deck
		 * @author tyler.hartleb1
		 */
		
		public void tableDraw() {
			tableHand.drawCard(super.getDeck(), 600, 192, 1200, 10, super.getCards(), this.tableCardsDrawn, super.getMainPane());
			super.cardsDrawnPlus();
			tableCardsDrawn++;
		}
		
		
		/**
		 * Draws a player card from the deck
		 * @author tyler.hartleb1
		 */
		
		public void playerDraw() {
			playerHand.drawCard(super.getDeck(), 990, 346, 1200, 10, super.getCards(), this.playerCardsDrawn, super.getMainPane());
			super.cardsDrawnPlus();
			playerCardsDrawn++;
		}
		
		
		/**
		 * Draws a dealer card from the deck
		 * @author tyler.hartleb1
		 */
		
		public void dealerDraw() {
			dealerHand.drawCard(super.getDeck(), 990, 46, 1200, 10, super.getCards(), this.dealerCardsDrawn, super.getMainPane());
			super.cardsDrawnPlus();
			dealerCardsDrawn++;
		}
		
		
		/**
		 * This method runs the War class winCondition which returns a winCondtion int,
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
				int winCond = War.winConditions(playerHand, dealerHand);
				if ( (winCond == 0) || (winCond == 1)) {
					if (super.getRoundOver() == true) {
						roundEnd(winCond);
					}
				}
			}
		}
		
		
	// Getter methods 
		
	public HandFX getTableHand() {
		return tableHand;
	}
		
	public HandFX getPlayerHand() {
		return playerHand;
	}
		

	// Setter Methods
	@Override
	public void zeroTotalDrawn() {
		super.zeroCardsDrawn();;
		this.playerHand.zeroCardCount();
		this.dealerHand.zeroCardCount();
		
	}

	@Override
	public void zeroDrawn() {
		super.zeroChipsDrawn();
		this.tableCardsDrawn = 0;
		this.playerCardsDrawn = 0;
		this.dealerCardsDrawn = 0;
		
	}
	
	
	// This method handle Action and Button Events
	
	
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
					
					if (event.getSource() == restart ) {
						zeroEndRound();
						clearBet();
						clearTable();
						setRound(10);
						newGame();
						newRound();
						saveFile();
					}
					
					if (event.getSource() == bet ) {
						roundStart();
						saveFile();
					}
					
		
					if ( event.getSource() == war ) {
						saveFile();
						goToWar();
						
					}
					if ( event.getSource() == clear ) {
						saveFile();
						clearBet();
						
					}
					
					if ( event.getSource() == surrender ) {
						surrender();
						turnOver();
						saveFile();
					}
				}
			};
			return handler;
		}
	
	

}
