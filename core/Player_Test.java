package core;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests Player class constructor, stored values and other methods
 */
public class Player_Test {

	/**
	 * Tests initially constructor values are at the proper default
	 */
	@Test
	public void test_Default_Constructor_Balance() {
		
		Player playerOne = new Player();
		
		assertEquals(500.00,playerOne.getBalance(), 0.001);
		
	}
	
	/**
	 * Tests that when new players are created, the correct number of players
	 * is returned by getNumberOfPlayers()
	 */
	@Test
	public void test_Number_Of_Players() {
		Player playerOne = new Player();
		Player playerTwo = new Player();
		Player playerThree = new Player();
		
		assertEquals(3, Player.getNumberOfPlayers());
	}
	
	/**
	 * Tests that number of loans and balance is updated when loanShark() is called
	 */
	@Test
	public void test_Loan_Shark() {
		Player playerOne = new Player();
		playerOne.loanShark();
		
		assertEquals(1000.00, playerOne.getBalance(), 0.001);
		assertEquals(1,playerOne.getNumberOfLoans());
	}
	
	/**
	 * Tests that negative inputs aren't added to balance through betWon()
	 */
	@Test
	public void test_Bet_Won() {
		Player playerOne = new Player();
		playerOne.betWon(-25.00);
		assertEquals("Negative bet balance should remain unchanged", 500.00, playerOne.getBalance(), 0.001);
	}
	
	/**
	 * Tests that negative inputs aren't taken from balance through betLost()
	 */
	@Test 
	public void test_Bet_Lost() {
		Player playerOne = new Player();
		playerOne.betLost(-50);
		assertEquals("Negative bet balance should remain unchanged", 500.00, playerOne.getBalance(), 0.001);
	}
	
	/**
	 * Tests that all the correct factors are impacting the player's score, 
	 * including number of loans and balance
	 */
	@Test
	public void test_Player_Score() {
		Player playerOne = new Player();
		playerOne.betWon(25223.4);
		for (int i = 0; i < 3; i++) {
			playerOne.loanShark();
		}
		playerOne.betLost(1467.3);
		playerOne.updatePlayerScore();
		
		assertEquals("Score should be 23756", 23756, playerOne.getScore());
	}
	
	/**
	 * Tests that the balance can be changed directly through setBalance()
	 */
	@Test
	public void test_Set_Balance() {
		Player playerOne = new Player();
		playerOne.setBalance(50000.00);
		
		assertEquals("Balance should be 50,000", 50000.00, playerOne.getBalance(), 0.001);
	}
	
}
