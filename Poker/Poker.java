package Poker; 
import java.util.Scanner; 
import java.util.ArrayList; 
import core.Deck;
import core.Hand;
import core.Card;  
import core.Player; 

/** 
 * Allows for playing poker in a text-based format. Style of poker is 5-draw,
 * where each player gets 5 cards each and may choose to exchange cards to get
 * the highest ranked hand. There is no AI, this is multiplayer only. 
 */

public class Poker {

	private static boolean betMade; // Has a bet been made this round?
	private static double currentBet; // The current highest bet to match
	private static Player[] players;  // Players who entered game
	private static PokerHand[] playerHands; // Hand of cards for each player
	private static int raisedPlayer; // The player with the highest bet
	private static double[] playerBets; // Saves player bets for the round
	private static Deck pokerDeck; // The deck to draw from

	Scanner sc = new Scanner (System.in); // Retrieves any user input

	/**
	 * The main method is used to set up the starting conditions of the game
	 * by taking initial user input. It also resets variables for and starts
	 * each round for a number of rounds defined by the player(s). 
	 */
	
	public static void main(String[] args)

	{
		Poker game = new Poker(); 

		Scanner sc = new Scanner (System.in);

		System.out.println("\n 		---POKER---\n");

		int playerNum = 0; // Selected number of players
		int inputs = 0; // Number of inputs by player for question

		while (playerNum > 5 || playerNum < 2) {

			if (inputs > 0) {

				System.out.println("	Not a valid number of players");
			}

			System.out.print("	Number of players (2 to 5): ");
			playerNum = sc.nextInt();
			inputs++; // If there is an error, will print notification next loop
		}

		players = new Player[playerNum]; 
		playerHands = new PokerHand[playerNum]; 
		pokerDeck = new Deck();

		for(int i = 0; i < playerNum; i++) 
		{
			players[i] = new Player();  
		}

		int roundNum = 0; // Number of rounds
		inputs = 0; 

        //Similar procedure to getting player number
		while (roundNum > 10 || roundNum < 1) {

			if (inputs > 0) {

				System.out.println("	Not a valid number of rounds");
			}

			System.out.print("\n	How many rounds will you be playing? (max 10): ");
			roundNum = sc.nextInt();
			inputs++;
		}

        // Resets necessary variables/objects and starts round the specified 
        // number of times
		for(int r = 1; r <= roundNum; r++)
		{ 
			System.out.println("\n   ROUND: " + r + "\n");

			pokerDeck.clearDeck();
			pokerDeck.createDeck(1); 
			pokerDeck.shuffleDeck();
			playerBets = new double[playerNum]; 
			betMade = false;
			currentBet = 0;
			raisedPlayer = -1; 

			for(int i = 0; i < playerNum; i++) 
			{
				players[i].setInRound(true); 
			}

			if (r > 1) 
			{
				for(int i = 0; i < playerNum; i++) 
				{
					playerHands[i].clearHand(); 
				}

			} else 
			{
				for(int i = 0; i < playerNum; i++) 
				{
					playerHands[i] = new PokerHand();  
				}
			}

			for(int n = 1; n <= 5; n++)
			{
				for(int i = 0; i < playerNum; i++) 
				{
					playerHands[i].drawCard(pokerDeck); 
				}
			}

			game.round(players); 	
		}

		System.out.println();
		System.out.println("\n    That concludes all the rounds. Goodbye!\n");

	}
	
	/**
	 * Runs a betting turn for the player with the index that's sent to it.
	 * @param i, the player index in players array 
	 */

	public void betTurn(int i) {

		System.out.println("\n    Hey " + players[i].getPlayerNumber() + ", it's your turn.\n");

		//Forced fold when balance is 0 for betting turns
		if (players[i].getBalance() == 0 || players[i].getBalance() < currentBet) {
			System.out.println("	You do not have the proper funds, you are forced to fold.");
			players[i].setInRound(false);
			return; 
		}
		
		//Display hand as text
		System.out.println("	Your hand is:");
		
		for (Card num : playerHands[i].getHandList()) {

				System.out.println("	" + num);
		}

        //Notifies player of bet that must be matched
		if(betMade) {

			System.out.println("\n	  Your current bet is: $" + playerBets[i]);

			if(currentBet > playerBets[i]) 
			{
				System.out.println("	You must match the bet of $" + currentBet + " by previous players");

			} else 
			{
				System.out.println("	You do not need to bet more to match other players");
			}	
		}

		int playerCommandIndex = 0; // Number related to menu selection
		int inputs = 0; // Helps check for errors in input

		//Error checking works similarly to input stored through main method
		while(playerCommandIndex < 1 || playerCommandIndex > 4) 
		{
			if (inputs > 0) System.out.println("\n 		That is not one of the options");

			System.out.println("\n 		Your current balance is: $" + players[i].getBalance());

			System.out.println("	What would you like to do?- enter the corresponding integer:");
		
			if (!betMade) 
			{
				System.out.println("	1) Bet");
				System.out.println("	2) Check");
				System.out.println("	3) Fold");
				System.out.println("	4) Exit- this will exit the game for all players");
			} else if (betMade)
				{
					System.out.println("	1) Call");
					System.out.println("	2) Raise");
					System.out.println("	3) Fold");
					System.out.println("	4) Exit- this will exit the game for all players");
				}	

			System.out.print("           ");	

			playerCommandIndex = sc.nextInt();

			inputs++; 
		}

        // Rotes through cases based on player selections, updating global 
        // variables if choice is relevant to subsequent turns
        // what each case specifically does depends on whether the first bet
        // of the round has already been made or not (see menu code directly above)
		switch (playerCommandIndex)
		{
			case 1: //Bet or Call
				if(!betMade) // Calling (match the current bet)
				{ 
					inputs = 0; 
					double firstBet = 0; 

					while (players[i].getBalance() < firstBet || firstBet <= 0) {

						if (inputs > 0) 
						{
							System.out.println("	Not a valid bet");
						}

						System.out.println("\n 	  How much are you betting?");
						System.out.print("	 Enter your bet: ");
						firstBet = sc.nextDouble();
						inputs++;
					}

					currentBet = firstBet;

					betMade = true; 

					playerBets[i] = currentBet;  

					raisedPlayer = i; 

				} else if (betMade) //raising bet
				{
					playerBets[i] = currentBet;

					ArrayList<Integer> check = getPlayersInRound(); 

					int lastInteger = 0; 
					
					updateRaised(i); 

				}
				break; 

			case 2: //Check or Raise
				if(betMade) // Raising the bet (nothing needs to be done for checking)
				{
					inputs = 0; 
					double raise = 0; 

					while (players[i].getBalance() < currentBet + raise || raise <= 0) {

						if (inputs > 0) 
						{
							System.out.println("	Not a valid raise");
						}

						System.out.println("	How much are you raising the bet by?");
						System.out.print("	  Enter your amount: ");
						raise = sc.nextDouble();
						inputs++;
					}

					currentBet += raise; 

					playerBets[i] = currentBet;

					raisedPlayer = i; 
				}

				break; 

			case 3: //Fold
				updateRaised(i);
				players[i].setInRound(false);

				break;

			case 4: //Exit entire game for all players
				System.out.println();
			    System.out.println("	Goodbye!");
                System.exit(0);
		}

		System.out.println("\n 		End of turn \n");

	}
	
	/**
	 * Runs a card exchange turn for the player with the index that's sent to it.
	 * @param i, the player index in players array
	 */

	public void drawTurn(int i) {

		System.out.println("\n    Hey " + players[i].getPlayerNumber() + ", it's your turn.\n");

		System.out.println("	Your hand is:");
		
        // Displays cards as text
		for (Card num : playerHands[i].getHandList()) {

				System.out.println("	" + num);
		}

		int playerCommandIndex;
		int inputs; 

		System.out.println("	Would you like to discard and redraw these cards? Type the corresponding integer...");

        // Allows for card exchange, asking about the replacement of each card one-by-one.
        // Goes from end of card list to start so that drawn cards do not interfere with
        // the placement of the next card (next card doesn't shift index)
		for(int j = 4; j >= 0; j--) 
		{  
			inputs = 0;
			playerCommandIndex = 0;

			while(playerCommandIndex < 1 || playerCommandIndex > 2) 
			{
				if (inputs > 0) System.out.println("\n 	 That is not one of the options");

				System.out.println("	Replace " + playerHands[i].getCard(j) + "?");
				System.out.println("	1) Yes");
				System.out.println("	2) No");

				System.out.print("           ");	
				playerCommandIndex = sc.nextInt();

				inputs++; 

			}

            // Replaces card if answer from player was yes
			switch(playerCommandIndex) {

				case 1: 
					playerHands[i].removeCard(j);
					playerHands[i].drawCard(pokerDeck);
					break;

				case 2:
					break; 
				//Do nothing (answer was no)

			}

		}

		System.out.println("\n 		End of turn \n");

	}

	/**
	 * Runs the 'showdown' at the end of each round, where players 'show' 
	 * their cards and the winner is declared
	 * @param playersInRound, the players that haven't folded yet
	 * @return winningIndex, the index of the winning player in players array
	 */

	public int showdown( ArrayList<Integer> playersInRound)
	{
		int winningIndex = 0; // index of winner in players array
		int highestHandValue = 0; // highest hand type among players
		int counter = 0; // number of highest hand type
		PokerHand forCalling = new PokerHand(); // used to make hand value comparison
		PokerHand winningHand; // returned by hand value comparison in PokerHand class

		System.out.println(); 

        // Displays hands of players still in round
		for (Integer i : playersInRound)
		{
			System.out.println("\n    " + players[i].getPlayerNumber() + "'s hand is:");

			if (highestHandValue < forCalling.handType(playerHands[i]).getHandRank()) 
			{
				highestHandValue = forCalling.handType(playerHands[i]).getHandRank();
			}

			for (Card num : playerHands[i].getHandList()) 
			{
				System.out.println("	" + num);
			}
		
		}

        // Counts if their are multiple highest hand types
		for (Integer i : playersInRound)
		{
			if (forCalling.handType(playerHands[i]).getHandRank() == highestHandValue)
			{
				counter++;
			}
		}

		ArrayList<PokerHand> sameHandType = new ArrayList<PokerHand>(); 

        // Adds same hand types to a list to call comparison method in PokerHand class
		if (counter > 1) 
		{
			for (Integer i : playersInRound)
			{
				if (forCalling.handType(playerHands[i]).getHandRank() == highestHandValue)
				{
					sameHandType.add(playerHands[i]);
				}
			}

			winningHand = forCalling.compareHandValue(sameHandType);

			for (Integer i : playersInRound)
			{
				if (playerHands[i] == winningHand)
				{
					winningIndex = i; 
				}
			}

		} else 
		{
			for (Integer i : playersInRound)
			{
				if (forCalling.handType(playerHands[i]).getHandRank() == highestHandValue)
				{
					winningIndex = i;
				}
			}

		} 

		return winningIndex; // Index of winning player in players array

	}
	
	/**
	 * Calls to make turns, showdown etc. are made from here. The structure of
	 * each round and the conditions under which they proceed is here
	 * @param players, the players that are playing the game
	 */

	public void round(Player[] players) {

		System.out.println("	Cards have been dealt. Now begins the first betting round");

		// Keeps the first betting round going while there is more than one player left
		// first go through of all player bets
		for(int i = 0; i < players.length; i++)
		{
			if(getPlayersInRound().size() < 2) break; 

			if (players[i].getInRound())
			{
				betTurn(i);
			}
		}

		int j = 0; //Used to cycle back to the beginning of players array if necessary to complete turns

		//Cycles through players a second time depending on the raised player
		//allowing for those under the bet to raise, call or fold
		if(raisedPlayer > 0) {

			while(raisedPlayer > -1) {

				if(getPlayersInRound().size() < 2) break; 

				if(j == players.length) j = 0; 

				if (players[j].getInRound())
				{
					betTurn(j);
				}

				j++; 

			}
		}
		
		if(getPlayersInRound().size() > 1)
		{
			System.out.println("	First betting round completed. Player may now choose to draw cards");
		}

        //Each player has 1 turn to replace 0-5 of their cards
		for(int i = 0; i < players.length; i++)
		{
			if(getPlayersInRound().size() < 2) break; 

			if (players[i].getInRound())
			{
				drawTurn(i);
			}
		}

		if(getPlayersInRound().size() > 1) 
		{
			System.out.println("	All players have been given the option to draw. Now second round of betting commences");
		}
		
		// Below code same as for first betting round

		for(int i = 0; i < players.length; i++)
		{	
			if(getPlayersInRound().size() < 2) break; 

			if (players[i].getInRound())
			{
				betTurn(i);
			}
		}

		j = 0; 

		while(raisedPlayer > 0) {

			if(getPlayersInRound().size() < 2) break; 

			if(j == players.length) j = 0; 

			if (players[j].getInRound())
			{
				betTurn(j);
			}

		}

		int winner; // winning index in players array

		// Gets winner by default (only one not folded) or through showdown
		if(getPlayersInRound().size() > 1) 
		{
			winner = showdown(getPlayersInRound()); 

		} else winner = getPlayersInRound().get(0); 

		double totalPot = 0; // Total bets by all players

		System.out.println("\n 	  " + players[winner].getPlayerNumber() + " is the winner! \n");

		// Updates loser balances 
		for(int i = 0; i < players.length; i++)
		{
			if(i != winner)
			{
				players[i].betLost(playerBets[i]);

				System.out.println("	  " + players[i].getPlayerNumber() + " loses $" + playerBets[i]);

				totalPot += playerBets[i]; 
			}
		}

        // Adds total pot to winner balance
		players[winner].betWon(totalPot);

		System.out.println("\n 	  " + players[winner].getPlayerNumber() + " wins $" + totalPot);
		
		//End of round

	}
	
	/**
	 * Returns the players still participating in the current round of poker
	 * @return playerInRound, the players that haven't folded yet
	 */

	public ArrayList<Integer> getPlayersInRound() {

		ArrayList<Integer> playersInRound = new ArrayList<Integer>(); 

		for (int i = 0; i < players.length; i++) 
		{
			if(players[i].getInRound())
			{
				playersInRound.add(i); 
			}
		}

		return playersInRound;  

	}
	
	/**
	 * Updates the player index of the player with the highest bet in the round
	 * @param i, the index of the player in players array
	 */

	public void updateRaised(int i) {

		ArrayList<Integer> check = getPlayersInRound(); 

		int lastInteger = 0; 

		if(i == check.get(check.size()-1))
		{
			if (raisedPlayer == check.get(0)) {

				raisedPlayer = -1; 
			}

		} else 
		{	
			for (Integer a : check) 
			{
				if (raisedPlayer == a && lastInteger == i)  
				{
					raisedPlayer = -1; 
				}

				lastInteger = a; 

			}
		}

	}

} 