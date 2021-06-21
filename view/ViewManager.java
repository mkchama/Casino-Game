package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.BlackJackButton;
import model.BlackJackSubScene;
import model.GAMES;
import model.GameChooser;
import model.infoLabel;


public class ViewManager {
	
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 220;
	
	
	private BlackJackSubScene playSubScene;
	private BlackJackSubScene creditsSubScene;
	private BlackJackSubScene rulesSubScene;
	private BlackJackSubScene exitSubScene;
	private BlackJackSubScene saveSubScene;
	private BlackJackSubScene sceneToHide;
	
	private List<BlackJackButton> menuButtons;
	
	
	private List<GameChooser> gamesList;
	private GAMES chosenGame;
	
	/**
	 * Initializes the menu
	 * @author mkchama
	 */
	
	
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		mainStage.getIcons().add(new Image("FX/resources/icon1.png"));
		
		createSubScenes();
		createButtons();
		createBackground();
		createLogo();
		
		

		
	}
	
	/**
	 * Displays the sub scenes in the main menu
	 * @param subScene
	 * @author mkchama
	 */
	
	
	private void showSubScene(BlackJackSubScene subScene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
		
	}
	
	/**
	 * Creates a sub scenes for each button in the main menu
	 * @author mkchama
	 */
	
	private void createSubScenes() {
		playSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(playSubScene);	
		
		creditsSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		saveSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(saveSubScene);

		rulesSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(rulesSubScene);	
		
		exitSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(exitSubScene);
		
		createGameChooserSubScene();
		createRulesSubScene();
		createCreditsSubScene();
		
		
		
		

	}
	
	/**
	 * Initializes the game chooser in the play sub scene
	 * Choose between War or Blackjack
	 * @author mkchama
	 */
	
	private void createGameChooserSubScene() {
		playSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(playSubScene);
		
		infoLabel chooseGameLabel = new infoLabel("CHOOSE YOUR GAME", 360, 60, 25);
		chooseGameLabel.setLayoutX(120);
		chooseGameLabel.setLayoutY(25);
		infoLabel warLabel = new infoLabel("War", 140, 55, 15);
		warLabel.setLayoutX(110);
		warLabel.setLayoutY(280);
		infoLabel bjLabel = new infoLabel("Blackjack", 140, 49, 15);
		bjLabel.setLayoutX(350);
		bjLabel.setLayoutY(280);
		playSubScene.getPane().getChildren().add(warLabel);
		playSubScene.getPane().getChildren().add(bjLabel);
		playSubScene.getPane().getChildren().add(chooseGameLabel);
		playSubScene.getPane().getChildren().add(createGamesToChoose());
		playSubScene.getPane().getChildren().add(createButtonToStart());
		
	}
	
	/**
	 * Initializes the rules in the rules sub scene
	 * Choose between War or Blackjack
	 * @author mkchama
	 */
	private void createRulesSubScene() {
		rulesSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(rulesSubScene);
		
		infoLabel warLabel = new infoLabel("                                                                                    War \n\n"
				+ "    Played with 5 decks. The player and dealer are each dealt\n  a card. Whoever has the higher card wins the wager. \n "
				+ "If the cards are of the same value, the player can Surrender \n or go to War. Going to War doubles"
				+ "the players bet. 3 cards are\n burned, and each player is drawn a card. The higher card wins.", 600, 238, 12);
		infoLabel bjLabel = new infoLabel("                                                                                    Blackjack \n\n"
				+ "    Played with 1 decks. The player and dealer are each dealt\n  a card. Whoever has the higher card wins the wager. \n "
				+ "If the cards are of the same value, the player can Surrender \n or go to War. Going to War doubles"
				+ "the players bet. 3 cards are\n burned, and each player is drawn a card. The higher card wins.", 600, 238, 12);
		warLabel.setLayoutX(0);
		warLabel.setLayoutY(0);
		bjLabel.setLayoutX(0);
		bjLabel.setLayoutY(220);
		
		rulesSubScene.getPane().getChildren().add(warLabel);
		rulesSubScene.getPane().getChildren().add(bjLabel);
		
	}
	
	/**
	 * Initializes the credits in the credits sub scene
	 * Choose between War or Blackjack
	 * @author mkchama
	 */
	
	private void createCreditsSubScene() {
		creditsSubScene = new BlackJackSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		infoLabel creditsLabel1 = new infoLabel("@tyler.hartleb1", 300, 90, 20);
		infoLabel creditsLabel2 = new infoLabel(" @mkchama", 300, 90, 20);
		infoLabel creditsLabel3 = new infoLabel("@serena.schimert", 300, 90, 20);
		infoLabel creditsLabel4 = new infoLabel("@zarif.qazi1 ", 300, 90, 20);
		infoLabel creditsLabel5 = new infoLabel("@sage.henderson1 ", 300, 90, 20);
		creditsLabel1.setLayoutX(150);
		creditsLabel1.setLayoutY(30);
		creditsLabel2.setLayoutX(150);
		creditsLabel2.setLayoutY(110);
		creditsLabel3.setLayoutX(150);
		creditsLabel3.setLayoutY(190);
		creditsLabel4.setLayoutX(150);
		creditsLabel4.setLayoutY(270);
		creditsLabel5.setLayoutX(150);
		creditsLabel5.setLayoutY(350);
		creditsSubScene.getPane().getChildren().add(creditsLabel1);
		creditsSubScene.getPane().getChildren().add(creditsLabel2);
		creditsSubScene.getPane().getChildren().add(creditsLabel3);
		creditsSubScene.getPane().getChildren().add(creditsLabel4);
		creditsSubScene.getPane().getChildren().add(creditsLabel5);
	}
	
	/**
	 * Initializes the games to choose inside the Play sub scene.
	 * @author mkchama
	 */
	
	private HBox createGamesToChoose() {
		HBox box = new HBox();
	
		gamesList = new ArrayList<>();
		for(GAMES game : GAMES.values()) {
			GameChooser gameToPick = new GameChooser(game);
			gamesList.add(gameToPick);
			box.getChildren().add(gameToPick);
			gameToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
				for (GameChooser game : gamesList){
					game.setIsCircleChosen(false);
				}
				gameToPick.setIsCircleChosen(true);
				chosenGame = gameToPick.getGames();
			}
			});
		}
		box.setLayoutX(366 - (118*2));
		box.setLayoutY(120);

		box.setSpacing(150);
		box.setAlignment(Pos.CENTER_RIGHT);
		
		
		
		return box;
	}
	
	/**
	 * Initializes the start button inside the play sub scene
	 * @author mkchama
	 */
	
	private BlackJackButton createButtonToStart() {
		BlackJackButton startButton = new BlackJackButton("START", 23, 49, 190);
		startButton.setLayoutX(200);
		startButton.setLayoutY(385);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			if (chosenGame != null) {
				GameViewManagerBJ gameManager = new GameViewManagerBJ();
				gameManager.createNewGame(mainStage, chosenGame);
			}
			}
		});
		
		return startButton;
	}
	
	/**
	 * Returns the mainStage
	 * @author mkchama
	 */

	public Stage getMainStage() {
		return mainStage;
	}
	
	/**
	 * Adds the menu button
	 * @param button
	 * @author mkchama
	 */
	private void addMenuButton(BlackJackButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	/**
	 * Initializes the buttons
	 * @author mkchama
	 */

	private void createButtons() {
		createPlayButton();
		createRulesButton();
		createCreditsButton();
		createExitButton();
		
		}
	
	/**
	 * These methods implement the button action
	 * @author mkchama
	 */
	
	//Play
	
	private void createPlayButton() {
		BlackJackButton playButton = new BlackJackButton("PLAY", 23, 49, 190);
		addMenuButton(playButton);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				showSubScene(playSubScene);
			}
		});
		
		
	}
	
	//Rules
	private void createRulesButton() {
		BlackJackButton rulesButton = new BlackJackButton("RULES", 23, 49, 190);
		addMenuButton(rulesButton);
		
		rulesButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				showSubScene(rulesSubScene);
			}
		});
		
	}
	
	//Credits
	
	private void createCreditsButton() {
		BlackJackButton creditsButton = new BlackJackButton("CREDITS", 23, 49, 190);
		addMenuButton(creditsButton);
		
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubScene);
			}
		});
	}
	
	//Exit
	
	private void createExitButton() {
		BlackJackButton exitButton = new BlackJackButton("EXIT", 23, 49, 190);
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
		

		
		
	}
	
	
	/**
	 * Initializes the background
	 * @author mkchama
	 */
	
	private void createBackground() {
		Image backgroundImage = new Image("gameFX/resources/greenfelt.jpg", 256,256,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));

	}
	/**
	 * Implements the logo in the menu
	 * @author mkchama
	 */
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logo3.png");
		logo.setFitHeight(157.5);
		logo.setFitWidth(300);
		logo.setLayoutX(400);
		logo.setLayoutY(25);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
		});
		mainPane.getChildren().add(logo);	
	}
}

