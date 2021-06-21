package War;
import java.util.Scanner;
import core.Deck;
import core.Hand;


import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import core.Deck;
import core.Card;
import core.Player;

public class War {

    private Hand playerHand, dealerHand, tableHand;
    private Double playerBalance;
    private Deck warDeck;
    private boolean playerRoundOver, validCommand;
    private double playerBet;
    private int playerDrawn, dealerDrawn;
    
    /**
	 * This method creates enum Commands for Betting and Exiting
	 * @author mkchama
	 */
    
    public enum Commands
    {
        Bet, Exit
    }

    public enum Wars
    {
        Yes, No
    }

    public War(double b) {
        this.playerBalance = b;
        this.dealerDrawn = 0;
        this.playerDrawn = 0;
    }

    public static void main(String[] args){

        System.out.println("---WAR---");

        War w = new War(500);
        w.initailizeDeck();

        Scanner sc = new Scanner (System.in);
    
        /**
	    * This loop ensures that there is enough money
	    * and checks win conditions for ties and win/losses
	    * @author mkchama
	    */
        
        while (w.playerBalance > 0)
        {
            w.playerHand.clearHand();
            w.dealerHand.clearHand();
            w.tableHand.clearHand();
            System.out.println("Your current balance is " + w.playerBalance);

            boolean validCommand = false;
            int playerCommandIndex = 0;
            int flag = 0;

            while (!w.playerRoundOver)
            {
                while (!validCommand)
                {

                    System.out.println("\nEnter a command: " + "\033[0;1m" +
                            java.util.Arrays.asList(Commands.values()) + "\033[0;0m");

                    String playerCommand = sc.next();

                    for (Commands c : Commands.values())
                    {
                        if (c.name().equals(playerCommand))
                        {
                            if (playerCommand.equals("Exit"))
                            {
                                validCommand = true;
                                break;
                            }
                            validCommand = true;
                            playerCommandIndex = Commands.valueOf(playerCommand).ordinal();
                            break;
                        }
                    }
                    //Checks for invalids commands
                    if (!validCommand)
                    {
                        System.out.println("Invalid Command.");
                    }
                    
                    //Bet Command

                    if (playerCommand.equals("Bet")) {
                        flag = 1;
                        while(true) {

                            System.out.print("Enter bet amount: ");

                            w.playerBet = sc.nextDouble();

                            if (w.playerBet <= 0 || w.playerBet > w.playerBalance) {
                                System.out.println("Bet invalid. Please enter a valid amount. \n");
                                continue;
                            }
                            break;
                        }
                    }

                    switch (playerCommandIndex) {
                        case 0: //Bet and checks hands against the dealer for possible Win Conditions
                            while (flag == 1) {
                                w.playerHand.drawCard(w.warDeck);
                                w.dealerHand.drawCard(w.warDeck);

                                if (w.playerHand.handValueW(w.playerHand) < w.dealerHand.handValueW(w.dealerHand)) {
                                    w.playerRoundOver = true;
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("Loss. Balance lowered by " + w.playerBet + "\n");
                                    w.playerBalance -= w.playerBet;
                                    flag = 0;
                                    break;
                                } else if (w.playerHand.handValueW(w.playerHand) > w.dealerHand.handValueW(w.dealerHand)) {
                                    w.playerRoundOver = true;
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("Win! Balance increased by " + w.playerBet + "\n");
                                    w.playerBalance += w.playerBet;
                                    flag = 0;
                                    break;
                                } else if (w.playerHand.handValueW(w.playerHand) == w.dealerHand.handValueW(w.dealerHand) && w.playerBalance >= 2*w.playerBet) {
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("\nWould you like to go to War? " + "\033[0;1m" +
                                            java.util.Arrays.asList(Wars.values()) + "\033[0;0m");
                                    String war = sc.next();
                                    if (war.equals("Yes")) {
                                        System.out.println("Going to war! Your bet is doubled: " + (w.playerBet * 2) + "\n");
                                        flag = 2;
                                    } else if (war.equals("No")) {
                                        flag = 3;
                                        break;
                                    }

                                }
                                else {
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("Insufficient balance to go to war." + "\n");
                                    flag = 3;
                                    break;
                                }

                            }

                        case 2: //If it goes to War, doubles the bet and burns three cards

                            while (flag == 2) {
                                w.playerHand.clearHand();
                                w.dealerHand.clearHand();
                                w.tableHand.drawCard(w.warDeck);
                                w.tableHand.drawCard(w.warDeck);
                                w.tableHand.drawCard(w.warDeck);
                                System.out.println("Burning three cards.");
                                w.playerHand.drawCard(w.warDeck);
                                w.dealerHand.drawCard(w.warDeck);

                                w.playerBet*=2;

                                if (w.dealerHand.handValueW(w.dealerHand) > w.playerHand.handValueW(w.playerHand)) {
                                    w.playerRoundOver= true;
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("Loss! Balance decreased by " + w.playerBet + "\n");
                                    w.playerBalance -= w.playerBet;
                                    flag = 0;
                                    break;
                                } else if (w.playerHand.handValueW(w.playerHand) > w.dealerHand.handValueW(w.dealerHand)) {
                                    w.playerRoundOver = true;
                                    systemOutput(w.playerHand, w.dealerHand);
                                    System.out.println("Win! Balance increased by " + w.playerBet + "\n");
                                    w.playerBalance += w.playerBet;
                                    flag = 0;
                                    break;
                                } else {
                                    System.out.println("\n\033[0;4m" + "Your Hand:" + "\033[0;0m" + w.playerHand);
                                    System.out.println("\n\033[0;4m" + "Dealer's Hand:" + "\033[0;0m" + w.dealerHand);
                                    System.out.println("War!" + "\n");
                                    System.out.println("\nWould you like to go to War? " + "\033[0;1m" +
                                            java.util.Arrays.asList(Wars.values()) + "\033[0;0m");
                                    String war = sc.next();
                                    if (war.equals("No")) {
                                        flag = 3;
                                        break;
                                    }
                                }
                            }

                        case 3: //Surrender reduces the players bet
                        {
                            while (flag == 3) {
                                System.out.println("Surrendered. Balance reduced by " + (w.playerBet) + "\n");
                                w.playerBalance -= w.playerBet;
                                w.playerRoundOver = true;
                                flag = 0;
                                break;
                            }
                        }

                        case 4: //Exit exits the game and breaks out of the loop
                        {
                            if (playerCommand.equals("Exit")) {
                                System.out.println("Your balance is: " + w.playerBalance + ". Goodbye!");
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            w.playerRoundOver=false;
        }
        System.out.println("Balance is zero. Better luck next time!");


    }

    //Prints out the hand of the player and dealer
    public static void systemOutput(Hand playerHand, Hand dealerHand)
    {
        System.out.println("\n\033[0;4m" + "Your Hand:" + "\033[0;0m" + playerHand);
        System.out.println("\n\033[0;4m" + "Dealer's Hand:" + "\033[0;0m" + dealerHand + "\n");
    }




    //Initializes the deck with 5 decks.
    public void initailizeDeck(){
        warDeck = new Deck();
        warDeck.createDeck(5);
        warDeck.shuffleDeck();
        playerHand = new Hand();
        dealerHand = new Hand();
        tableHand = new Hand();
        playerRoundOver = false;
        validCommand = false;

    }
    //Getter methods
    
    public boolean getRoundOver() {
        return this.playerRoundOver;
    }

    public void setRoundOver(Boolean a) {
        this.playerRoundOver = a;
    }

    public Hand getPlayerHand() {
        return this.playerHand;
    }
    
    public int getPlayerDrawn() {
    	return playerDrawn;
    }

    public Hand getDealerHand() {
        return this.dealerHand;
    }

    public Deck getWarDeck() {
        return this.warDeck;
    }

    public double getBalance() {
        return this.playerBalance;
    }
    //Win Conditions used in WarGameGUI
    public static int winConditions(Hand playerHand, Hand dealerHand) {
		int winCond = 5;
		System.out.println(playerHand.handValueW(playerHand.getHand()));
		System.out.println(dealerHand.handValueW(dealerHand.getHand()));
		if (playerHand.handValueW(playerHand.getHand())  > dealerHand.handValueW(dealerHand.getHand())) {
			winCond = 0;
		}
		else if (dealerHand.handValueW(dealerHand.getHand()) > playerHand.handValueW(playerHand.getHand())) {
			winCond = 1;
		}
		
			
		return winCond;
	}

}