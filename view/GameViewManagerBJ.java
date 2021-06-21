package view;

import gameFX.BlackjackGameGui;
import javafx.stage.Stage;
import model.GAMES;
import gameFX.WarGameGui;

public class GameViewManagerBJ {
	
	public GameViewManagerBJ() {
		
		
	}
	/**
	 * Determines what game was chosen
	 * @param menuStage
	 * @param chosenGame
	 * @author mkchama
	 */
	public void createNewGame(Stage menuStage, GAMES chosenGame) {
		menuStage.hide();
		if (chosenGame.getUrl() == "view/resources/gamechooser/bjnew.png") {
			BlackjackGameGui blackjackGame = new BlackjackGameGui();
			blackjackGame.initGame(menuStage);
		}
		else if (chosenGame.getUrl() == "view/resources/gamechooser/newwar.png") {
			WarGameGui warGame = new WarGameGui();
			warGame.initGame(menuStage);
		}
		else {
			//Do nothing
		}
	}

}

