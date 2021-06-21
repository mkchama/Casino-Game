package gameFX;

import java.io.*;
import java.util.Scanner;

import core.ChipHand;
import core.Chips;
import core.DeckFX;
import core.Chips.Chip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.infoLabel;

public abstract class GameFX {
	
	private static final int HEIGHT = 720;
	private static final int WIDTH = 1360;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private infoLabel betAmount, maxBet, chipsAmountWhite, chipsAmountRed, chipsAmountBlue, chipsAmountGreen, chipsAmountBlack, balanceLabel, winLose, roundTurn;
	private infoLabel whiteAmount, redAmount, blueAmount, greenAmount, blackAmount;
	private int cardsDrawn, betInt, whiteDrawn, redDrawn, blueDrawn, greenDrawn, blackDrawn, round, turn;
	private ImageView whiteImage, blueImage, redImage, greenImage, blackImage;
	private StackPane white, blue, red, green, black;
	private Boolean roundStart, roundOver;
	private VBox buttonBoxSettings;
	private ChipHand playerChips;
	private int balance = 500, maxBetInt = 100;
	private final String FONT_PATH = "model/resources/kenvector_future.ttf";
	private DeckFX deck1;
	private Scanner x;
	protected int loadBalance;
	
	public GameFX() {
		this.mainPane = new AnchorPane();
		this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		this.mainStage = new Stage();
		this.round = 1;
		this.turn = 1;
		this.roundStart = false;
		this.roundOver = false;
		this.cardsDrawn = 0;
	}
	

	public abstract void initGame(Stage menuStage);
	
	public abstract void initButtons();
	
	protected void initLabel() {
		betAmount = new infoLabel("Bet: 0" , 190, 49, 15);	
		
		openFile();
		
		maxBet = new infoLabel("Max Bet: 100", 190, 49, 12);
		
		balanceLabel = new infoLabel("Balance: " + balance, 190, 49, 15);
		
		roundTurn = new infoLabel("Round: " + getRound() + " Turn: " + getTurn(), 190, 49, 15);
		
		winLose = new infoLabel("", 190, 49, 12);
		
		
		buttonBoxSettings = new VBox();
		
		buttonBoxSettings.setLayoutX(10);
		buttonBoxSettings.setLayoutY(10);
		buttonBoxSettings.setSpacing(10);
		
		getMainPane().getChildren().addAll(buttonBoxSettings);
		
		buttonBoxSettings.getChildren().addAll(balanceLabel, maxBet, betAmount, roundTurn);
	}
	/**
	 * This method opens the file, and creates a new one if one does not
	 * exist
	 * @author mkchama
	 */
	private void openFile() {
		File inputFile = new File("saveFile.txt");
		try {
			inputFile.createNewFile();
		} catch (IOException e1) {
			System.out.println("File already exists");
			e1.printStackTrace();
		}
		BufferedReader inputReader;
		try {
			inputReader = new BufferedReader(new FileReader(inputFile));
			loadBalance = Integer.parseInt(inputReader.readLine());
			System.out.println(loadBalance);
			inputReader.close();
				System.out.println(loadBalance);
				balance = loadBalance;

		}
		catch (Exception e) {
			System.out.println("Could not find file.");
		}
	}
	/**
	 * This method saves the balance to the file and throws an 
	 * exception if it cannot save.
	 * @author mkchama
	 */
	protected void saveFile() {
		File outputFile;
		BufferedWriter outputWriter;
		
		try {
			outputFile = new File("saveFile.txt");
			outputWriter = new BufferedWriter(new FileWriter("saveFile.txt"));
			outputWriter.write(Integer.toString(getBalance()));
			outputWriter.close();
		}	
		catch (Exception e) {
			System.out.println("Could not save file.");
		}
	}
		

	
	protected void initChipsDealer() {
		whiteImage = new ImageView("/chips/chip_white_one.png");
		whiteAmount = new infoLabel("1", 24, 24, 10);
		whiteImage.addEventHandler(MouseEvent.MOUSE_CLICKED, chipActionsDealer());
		
		redImage = new ImageView("/chips/chip_red_five.png");
		redAmount = new infoLabel("5", 24, 24, 10);
		redImage.addEventHandler(MouseEvent.MOUSE_CLICKED, chipActionsDealer());
		
		blueImage = new ImageView("/chips/chip_blue_ten.png");
		blueAmount = new infoLabel("10", 24, 24, 10);
		blueImage.addEventHandler(MouseEvent.MOUSE_CLICKED, chipActionsDealer());
		
		greenImage = new ImageView("/chips/chip_green_twentyfive.png");
		greenAmount = new infoLabel("25" ,24 ,24, 10);
		greenImage.addEventHandler(MouseEvent.MOUSE_CLICKED, chipActionsDealer());
		
		blackImage = new ImageView("/chips/chip_black_onehundred.png");
		blackAmount = new infoLabel("100", 24, 24, 10);
		blackImage.addEventHandler(MouseEvent.MOUSE_CLICKED, chipActionsDealer());
		
		showDealerChips(210, 630);	
		getMainPane().getChildren().addAll(whiteImage, redImage, blueImage, greenImage, blackImage, whiteAmount, redAmount, blueAmount, greenAmount, blackAmount);
	}
	
	
	/**
	 * This method initializes the player chips
	 * @author tyler.hartleb1
	 */
	
	protected void initChipsPlayer() {
		this.playerChips = new ChipHand(true);

		white = new StackPane();
		chipsAmountWhite = new infoLabel("" + whiteDrawn, 24, 24, 12);
		white.setLayoutX(whiteImage.getLayoutX());
		white.setLayoutY(whiteImage.getLayoutY() - 74);
		white.getChildren().add(chipsAmountWhite);
		chipsAmountWhite.setVisible(false);
		
		red = new StackPane();
		chipsAmountRed = new infoLabel("" + redDrawn, 24, 24, 12);
		red.setLayoutX(redImage.getLayoutX());
		red.setLayoutY(redImage.getLayoutY() - 74);
		red.getChildren().add(chipsAmountRed);
		chipsAmountRed.setVisible(false);
		
		blue = new StackPane();
		chipsAmountBlue = new infoLabel("" + blueDrawn, 24, 24, 12);
		blue.setLayoutX(blueImage.getLayoutX());
		blue.setLayoutY(blueImage.getLayoutY() - 74);
		blue.getChildren().add(chipsAmountBlue);
		chipsAmountBlue.setVisible(false);
		
		green = new StackPane();
		chipsAmountGreen = new infoLabel("" + greenDrawn, 24, 24, 12);
		green.setLayoutX(greenImage.getLayoutX());
		green.setLayoutY(greenImage.getLayoutY() - 74);
		green.getChildren().add(chipsAmountGreen);
		chipsAmountGreen.setVisible(false);
		
		black = new StackPane();
		chipsAmountBlack = new infoLabel("", 24, 24, 12);
		black.setLayoutX(blackImage.getLayoutX());
		black.setLayoutY(blackImage.getLayoutY() - 74);
		black.getChildren().add(chipsAmountBlack);
		chipsAmountBlack.setVisible(false);
		
		getMainPane().getChildren().addAll(white, red, blue, green, black);
	}
	
	
	/**
	 * This method initializes the deck and displays it to the gui
	 * @author tyler.hartleb1
	 */
	
	protected void initDeck() {
		ImageView deck = new ImageView("gameFX/resources/deck.png");
		deck.setLayoutX(1200);
		deck.setLayoutY(10);
		
		
		getMainPane().getChildren().removeAll(deck);
		getMainPane().getChildren().addAll(deck);
		this.deck1 = new DeckFX();
		zeroTotalDrawn();
		deck1.showCard(getMainPane(), 1200, 10, cardsDrawn);
	}
	
	public void showDealerChips(int x, int y) {
		whiteImage.setLayoutX(x);
		whiteAmount.setLayoutX(x);
		redImage.setLayoutX(x+74);
		redAmount.setLayoutX(x+74);
		blueImage.setLayoutX(x + (74 * 2));
		blueAmount.setLayoutX(x + (74 * 2));
		greenImage.setLayoutX(x + (74 *3));
		greenAmount.setLayoutX(x + (74 * 3));
		blackImage.setLayoutX(x + (74 * 4));
		blackAmount.setLayoutX(x + (74 * 4));
		
		whiteImage.setLayoutY(y);
		whiteAmount.setLayoutY(y);
		redImage.setLayoutY(y);
		redAmount.setLayoutY(y);
		blueImage.setLayoutY(y);
		blueAmount.setLayoutY(y);
		greenImage.setLayoutY(y);
		greenAmount.setLayoutY(y);
		blackImage.setLayoutY(y);
		blackAmount.setLayoutY(y);
	}
	
	
	/**
	 * Takes in the label, and the number of chips drawn in order to update 
	 * the label that displays over the chip
	 * @param aLabel
	 * @param chipsDrawn
	 * @author tyler.hartleb1
	 */
	
	public void updateLabel(infoLabel aLabel, int chipsDrawn) {
		aLabel.setText("" + chipsDrawn);
		aLabel.toFront();
		aLabel.setVisible(true);
	}
	
	
	/**
	 * Takes in the value of the chip and calculates if it will exceed either the
	 * player balance or maximum bet amount, then display it in the maxBet label for a
	 * certain duration of time
	 * @param aInt
	 * @author tyler.hartleb1
	 */
	
	public void animateUpdateLabel(int aInt) {
		if ( (getBet() + aInt) > maxBetInt) {
			maxBet.setText("Exceeds max bet");
		}
		else {
			maxBet.setText("Insufficient Balance");
		}
		Timeline animateLabel = new Timeline(
				new KeyFrame(Duration.seconds(2)));
		animateLabel.setCycleCount(1);
		animateLabel.setOnFinished(event -> maxBet.setText("Max Bet: " + maxBetInt) );
		animateLabel.play();
	}
	
	
	public abstract void animateUpdateRound();
	
	
	public abstract void clearTable();
	
	
	/**
	 * This method zeroes the bet and cards drawn
	 * @author tyler.hartleb1
	 */
	
	public void zeroEndRound() {
		resetTurn();
		zeroBet();
		zeroDrawn();
	}
	
	
	/**
	 * This method removes any chips from the chip stacks
	 * @author tyler.hartleb1
	 */
	
	public void clearChips() {
		white.getChildren().clear();
		red.getChildren().clear();
		blue.getChildren().clear();
		green.getChildren().clear();
		black.getChildren().clear();
		getMainPane().getChildren().removeAll(white,red,blue,green,black);
	}
	
	
	/**
	 * This method removes clears the bet made
	 * @author mkchama
	 */
	
	public void clearBet() {
		clearChips();
		redDrawn = 0;
		blackDrawn = 0;
		blueDrawn = 0;
		blackDrawn = 0;
		greenDrawn=0;
		
		setBetLabel(0);
		addBet(-getBet());
		initChipsPlayer();
		initChipsDealer();
		
	}


	public void createBackground() {
		Image backgroundImage = new Image("gameFX/resources/greenfelt.jpg", 256,256,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));

	}
	
	
	// These are the setter methods
	
	
	public void setBalanceLabel(int balanceInt) {
		
	}
	public void setBetLabel(int betInt) {
		betAmount.setText("Bet: " + betInt);
	}
	
	public void roundPlus() {
		round++;
	}
	
	public void turnPlus() {
		turn++;
	}
	
	public void zeroChipsDrawn() {
		this.whiteDrawn = 0;
		this.redDrawn = 0;
		this.blueDrawn = 0;
		this.greenDrawn = 0;
		this.blackDrawn = 0;
	}
	
	public void zeroCardsDrawn() {
		this.cardsDrawn = 0;
	}
	
	public void cardsDrawnPlus() {
		cardsDrawn++;
	}
	
	public void resetTurn() {
		this.turn = 1;
	}
	
	public void setRound(int aInt) {
		this.round = aInt;
	}
	
	
	public void setRoundStart(Boolean bool) {
		this.roundStart = bool;
	}
	
	
	public void setRoundOver(Boolean bool) {
		this.roundOver = bool;
	}
	
	public void addBet(int betAmount) {
		this.betInt += betAmount;
	}
	
	
	public void zeroBet() {
		this.betInt = 0;
	}
	
	public void clearBalance() {
		this.balance = 0;
	}
	
	public void updateBalance(int amount) {
		this.balance += amount;
	}
	
	
	public void setRoundTurn() {
		roundTurn.setText("Round: " + getRound() + " Turn: " + getTurn());
	}
	
	public abstract void zeroTotalDrawn();
	
	
	public abstract void zeroDrawn(); 
	
	
	public void setCardsDrawn() {
		this.cardsDrawn = cardsDrawn++;
	}
	
	
	// These are the getter methods
	
	
	public int getBet() {
		return betInt;
	}
	
	public infoLabel getRoundTurn() {
		return roundTurn;
	}
	
	
	public infoLabel getWinLose() {
		return winLose;
	}
	
	
	public infoLabel getBalanceLabel() {
		return balanceLabel;
	}
	
	public VBox getButtonBoxSettings() {
		return buttonBoxSettings;
	}
	
	public int getLoadBalance() {
		return loadBalance;
	}
	
	public int getBalance() {
		return balance;
	}
		
		
	public boolean getRoundStart() {
		return roundStart;
	}
		
	public boolean getRoundOver() {
		return roundOver;
	}
	
	
	public int getCards() {
		return cardsDrawn;
	}
		
	public DeckFX getDeck() {
		return deck1;
	}
		
		
	public int getWhiteDrawn() {
		return whiteDrawn;
	}
		
		
	public int getRedDrawn() {
		return redDrawn;
	}
		
		
	public int getBlueDrawn() {
		return blueDrawn;
	}
		
		
	public int getGreenDrawn() {
		return greenDrawn;
	}
		
		
	public int getBlackDrawn() {
		return blackDrawn;
	}
	
	
	public int getHeight() {
		return HEIGHT;
	}
	
	
	public int getWidth() {
		return WIDTH;
	}
	
	
	public AnchorPane getMainPane() {
		return mainPane;
	}
	
	
	public Scene getScene() {
		return mainScene;
	}
	
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	
	public int getTurn() {
		return turn;
	}


	public int getRound() {
		return round;
	}
	
	
	/**
	 * This method returns a EventHandler for mouse events for the chips,
	 * no event will happen if the round has started
	 * @return
	 * @author tyler.hartleb1
	 */
	
	public EventHandler<MouseEvent> chipActionsDealer() {
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int x = 210;
				int y = 630;
				if ( !(getRoundStart() == true) ) {
					if ( event.getSource() == whiteImage) {
						if (!( ( getBet() + 1 ) > maxBetInt) && ( !( (getBet() + 1) > balance) ) ) {
							addBet(1);
							setBetLabel(getBet());
							playerChips.drawChip(new Chips(Chip.White), 0, getMainPane(), white, x, y);
							whiteDrawn++;
							updateLabel(chipsAmountWhite, getWhiteDrawn());
						}
						else {
							animateUpdateLabel(1);
						}
					}
					
					if ( event.getSource() == redImage) {
						if ( !( ( getBet() + 5) > maxBetInt) && ( !( (getBet() + 5) > balance) ) ) {
							addBet(5);
							setBetLabel(getBet());
							playerChips.drawChip(new Chips(Chip.Red), 0, getMainPane(), red, (x+74), y);
							redDrawn++;
							updateLabel(chipsAmountRed, getRedDrawn());	
						}
						else {
							animateUpdateLabel(5);
						}
					}
					
					if ( event.getSource() == blueImage) {
						if ( !( ( getBet() + 10) > maxBetInt) && ( !( (getBet() + 10) > balance) ) ) {
							addBet(10);
							setBetLabel(getBet());
							playerChips.drawChip(new Chips(Chip.Blue), 0, getMainPane(), blue, (x+ (74 * 2) ), y);
							blueDrawn++;
							updateLabel(chipsAmountBlue, getBlueDrawn());
						}
						else {
							animateUpdateLabel(10);
						}
					}
					
					if ( event.getSource() == greenImage) {
						if ( !( ( getBet() + 25) > maxBetInt) && ( !( (getBet() + 25) > balance) ) ) {
							addBet(25);
							setBetLabel(getBet());
							playerChips.drawChip(new Chips(Chip.Green), 0, getMainPane(), green, (x + (74 * 3) ), y);
							greenDrawn++;
							updateLabel(chipsAmountGreen, getGreenDrawn());
						}
						else {
							animateUpdateLabel(25);
						}
					}
					
					if ( event.getSource() == blackImage) {
						if ( !( ( getBet() + 100) > maxBetInt) && ( !( (getBet() + 100) > balance) ) ) {
							addBet(100);
							setBetLabel(getBet());
							playerChips.drawChip(new Chips(Chip.Black), 0, getMainPane(), black, (x + (74 * 4) ), y);
							blackDrawn++;
							updateLabel(chipsAmountBlack, getBlackDrawn());
						}
						else {
							animateUpdateLabel(100);
						}
					
					}
				}
				
			}
		};
		return handler;
	}
}