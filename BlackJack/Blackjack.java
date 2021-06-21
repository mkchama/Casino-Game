package BlackJack;
import java.util.Scanner;

import core.Deck;
import core.Hand;

public class Blackjack {
	
	private Hand playerHand, dealerHand; //hands for the player and dealer
	private Double playerBalance; //player balance amount
	private Deck blackjackDeck; //deck from which the game will be played
	private boolean playerRoundOver; //check to see status of player turn
	private double playerBet; //player bet amount

    public enum Commands //possible player commands in blackjack
    {
        Hit(0), Stand(1), Double(2), Surrender(3), DealerHand(4), Exit(5); //each command is assigned to an integer value
    	
    	private int commandValue;
    	
    	Commands(int value) {
    		this.commandValue = value; //sets command value
    	}
    	
    	public int getCommandValue(){
    		return commandValue; //returns command value
    	}
    }
    
    public Blackjack(double b) {
    	this.playerBalance = b;
    }

    public static void main(String[] args)
    {
        System.out.println("---BLACKJACK---");
        Blackjack bj = new Blackjack(500);
        bj.initializeDeck();

       Scanner sc = new Scanner(System.in);

       while (bj.playerBalance > 0)
       {
           bj.playerHand.clearHand(); //player hand is cleared at beginning of new round
           bj.dealerHand.clearHand(); //dealer hand is cleared at beginning of new round

           System.out.println("Your current balance is " + bj.playerBalance + ", how much are you betting?");
           System.out.print("Enter bet amount: ");

           bj.playerBet = sc.nextDouble();

           if (bj.playerBet < 0 || bj.playerBet > bj.playerBalance)
           {
               System.out.println("Bet invalid. Please enter a valid amount. \n");
               continue;
           }

           for (int i = 0; i < 2; i++)
           {
               if (Hand.cardCount > 40)
               {
                   bj.blackjackDeck.shuffleDeck();
                   Hand.cardCount = 0;
               }
               bj.playerHand.drawCard(bj.blackjackDeck);
               bj.dealerHand.drawCard(bj.blackjackDeck);
           }

           boolean validCommand = false; //checks for if player command is valid
           int playerCommandIndex = 0;
           int pickCount = 0;

            while (!bj.playerRoundOver)
            {
                while (!validCommand)
                {
                    System.out.println("\n\033[0;4m" + "Your Hand:" + "\033[0;0m" +
                            bj.playerHand + "\n\nEnter a command: " + "\033[0;1m" +
                            java.util.Arrays.asList(Commands.values()) + "\033[0;0m");

                    String playerCommand = sc.next();

                    for (Commands c : Commands.values())
                    {
                        if (c.name().equals(playerCommand))
                        {
                            if (playerCommand.equals("Surrender") && pickCount > 0)
                            {
                                System.out.println("Surrender only available in first pick.");
                                continue;
                            }
                            else if (playerCommand.equals("Double") && bj.playerBalance < (bj.playerBet * 2))
                            {
                                System.out.println("Insufficient Balance to double.");
                                continue;
                            }
                            validCommand = true;
                            playerCommandIndex = Commands.valueOf(playerCommand).ordinal();
                            pickCount++;
                            break;
                        }
                    }
                    if (!validCommand)
                    {
                        System.out.println("Invalid Command.");
                    }

                }

                switch (playerCommandIndex)
                {
                    case 0: //Hit
                        bj.playerHand.drawCard(bj.blackjackDeck);
                        if (bj.playerHand.handValue(bj.playerHand) > 21)
                        {
                            bj.playerRoundOver = true;
                            System.out.println("\n\033[0;4m" + "Your Hand:" + "\033[0;0m" + bj.playerHand);
                            System.out.println("Bust. Balance lowered by " + bj.playerBet + "\n");
                            bj.playerBalance -= bj.playerBet;
                            break;
                        }
                        else
                        {
                            validCommand = false;
                            continue;
                        }
                    case 1: //Stand
                        bj.playerRoundOver = true;
                        while (bj.dealerHand.handValue(bj.dealerHand) < 17 && Hand.cardCount < 52)
                        {
                            bj.dealerHand.drawCard(bj.blackjackDeck);
                        }
                        int winCond = winCondition(bj.playerHand, bj.dealerHand);
                        
                        if (winCond == 1)
                        {
                            systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("Win! Balance increased by " + bj.playerBet + "\n");
                            bj.playerBalance += bj.playerBet;
                            break;
                        }
                        else if (winCond == 2)
                        {
                        	systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("Tie. Balance unaltered." + "\n");
                            break;
                        }
                        else
                        {
                        	systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("You Lost. Balance reduced by " + bj.playerBet + "\n");
                            bj.playerBalance -= bj.playerBet;
                            break;
                        }

                    case 2: //Double
                        bj.playerBet = bj.playerBet * 2;
                        bj.playerHand.drawCard(bj.blackjackDeck);
                        bj.playerRoundOver = true;
                        while (bj.dealerHand.handValue(bj.dealerHand) < 17 && Hand.cardCount < 52)
                        {
                            bj.dealerHand.drawCard(bj.blackjackDeck);
                        }
                        int winCond2 = winCondition(bj.playerHand, bj.dealerHand);
              
                        if (winCond2 == 1)
                        {
                        	systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("Win! Balance increased by " + bj.playerBet + "\n");
                            bj.playerBalance += bj.playerBet;
                            break;
                        }
                        else if (winCond2 == 2)
                        {
                        	systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("Tie. Balance unaltered." + "\n");
                            break;
                        }
                        else
                        {
                        	systemOutput(bj.playerHand, bj.dealerHand);
                            System.out.println("You Lost. Balance reduced by " + bj.playerBet + "\n");
                            bj.playerBalance -= bj.playerBet;
                            break;
                        }

                    case 3: //Surrender
                        System.out.println("Surrendered. Balance reduced by " + (bj.playerBet / 2) + "\n");
                        bj.playerBalance -= (bj.playerBet / 2);
                        bj.playerRoundOver = true;
                        break;

                    case 4: //DealerHand
                        System.out.println("\n\033[0;4m" + "Dealer's Hand:" + "\033[0;0m" + bj.dealerHand);
                        validCommand = false;
                        continue;

                    case 5: //Exit
                        System.out.println("Goodbye.");
                        sc.close();
                        System.exit(0);
                }
            }
            bj.playerRoundOver=false;
       }
           System.out.println("Balance is zero. Better luck next time!");
    }
    
    public static void systemOutput(Hand playerHand, Hand dealerHand)
    {
        System.out.println("\n\033[0;4m" + "Your Hand:" + "\033[0;0m" + playerHand);
        System.out.println("\n\033[0;4m" + "Dealer's Hand:" + "\033[0;0m" + dealerHand + "\n");
    }
    
    /**
    * initializes deck, hands, and boolean for round status check
    */
    public void initializeDeck() {
        blackjackDeck = new Deck();
        blackjackDeck.createDeck(1);
        blackjackDeck.shuffleDeck();
        playerHand = new Hand();
        dealerHand = new Hand();
        playerRoundOver = false;
    }
    
    /**
    * sets player balance
    */
    public void setBalance(double b) {
    	this.playerBalance += b;
    }

    /**
    * returns player balance
    */
    public double getBalance() {
        return this.playerBalance;
    }

    /**
    * returns status of player round
    */   
    public boolean getRoundOver() {
    	return this.playerRoundOver;
    }
    
    /**
    * sets player round status to be over
    */
    public void setRoundOver(Boolean a) {
    	this.playerRoundOver = a;
    }
    
    /**
    * returns player hand
    */
    public Hand getPlayerHand() {
    	return this.playerHand;
    }
    
    /**
    * returns dealer hand
    */
    public Hand getDealerHand() {
    	return this.dealerHand;
    }
    
    /**
    * returns playing deck for blackjack
    */
    public Deck getBlackjackDeck() {
    	return this.blackjackDeck;
    }
    
	public static int winCondition(Hand playerHand, Hand dealerHand) {
		int winCond = 3;
		if ((playerHand.handValue(playerHand.getHand())) > 21) {
			winCond = 0;
		}
		else if ((dealerHand.handValue(dealerHand.getHand())) > 21) {
			winCond = 1;
		}
		else if (dealerHand.handValue(dealerHand.getHand()) >= 17 &&
				dealerHand.handValue(dealerHand.getHand()) <= 21) {
			if (playerHand.handValue(playerHand.getHand()) > dealerHand.handValue(dealerHand.getHand())) {
				winCond = 1;
			}
			else if (dealerHand.handValue(dealerHand.getHand()) > playerHand.handValue(playerHand.getHand())) {
				winCond = 0;
			}
			else if (dealerHand.handValue(dealerHand.getHand()) == playerHand.handValue(playerHand.getHand())) {
				winCond = 2;
			}
			else {
				winCond = 3;
			}
		}
		else {
			winCond = 3;
		}	
		return winCond;
	}
}