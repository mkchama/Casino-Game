package core; 

public class Player {

	private double balance;
	private String playerNumber;
	private int numberOfLoans;
	private int score;  
	private boolean inRound;
	private static int numberOfPlayers; 

	public static void main(String[] args) {}
	
	/**
	 * Default constructor updating static counter for number of players 
	 */

	public Player() {

		numberOfPlayers++;
		this.playerNumber = "Player " + numberOfPlayers; 
		this.balance = 500.00; 
		this.numberOfLoans = 0;
		this.score = 0;
		this.inRound = true; 
	}
	
	public Player(String accountString) {
	    
	String[] stringArray = accountString.split(":");
	if (stringArray.length == 5){
	numberOfPlayers++;
	this.playerNumber = stringArray[0];
	this.balance = Double.parseDouble(stringArray[1]);
	this.numberOfLoans = 0;
	this.score =0;
	this.inRound = true;
	}
	else{
	    numberOfPlayers++;
		this.playerNumber = "Player " + numberOfPlayers; 
		this.balance = 500.00; 
		this.numberOfLoans = 0;
		this.score = 0;
		this.inRound = true; 
	}
	}
	
	public String accountToString(){
	    String saveFileString;
	    saveFileString = playerNumber + ":" + balance + ":";
	    saveFileString += numberOfLoans + ":" + score + ":";
	    saveFileString += inRound;
	    return saveFileString;
	}

	public void loanShark() {

			this.balance += 500; 
			this.numberOfLoans++;
		
	}

	public void betLost(double bet) 
	{
		if(bet > 0 && bet <= balance)
			this.balance -= bet; 
	}

	public void betWon(double winnings)
	{
		if(winnings > 0)
			this.balance += winnings; 
	}

	// To be called anytime a player exits a game
	public void updatePlayerScore()
	{
		this.score = (int)(getBalance() - 500) - (getNumberOfLoans()*500); 
	}

	public double getBalance() {

		return balance; 
	}
	
	public void setBalance(Double amount) {
		balance = amount;
	}

	public String getPlayerNumber() {

		return playerNumber; 
	}

	public int getNumberOfLoans() {

		return numberOfLoans; 
	}

	public boolean getInRound() {

		return inRound;
	}

	public void setInRound(boolean in) {

		this.inRound = in; 
	}

	public static int getNumberOfPlayers() {

		return numberOfPlayers;
	}
	
	public int getScore() {
		return score;
	}

}