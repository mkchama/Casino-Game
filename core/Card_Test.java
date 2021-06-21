package core;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Junit tests for Card class methods 
 */
public class Card_Test { 

	/**
	 * Tests that suit and rank are set according to the input
	 * to the constructor
	 */
	@Test
	public void test_Constructor() {

		Card.Suit suitTest = Card.Suit.Spades;
		Card.Rank rankTest = Card.Rank.Ten;

		Card testCard = new Card(suitTest, rankTest);

		Card.Suit suit = testCard.getSuit();
		Card.Rank rank = testCard.getRank();

		assertEquals(suitTest, suit);
		assertEquals(rankTest, rank);
	}

    /**
     * Tests that the suit and rank enum value getters return the correct
     * numerical values
     */
	@Test
	public void test_Enum_Value_Getters() {

		Card.Suit suitTest = Card.Suit.Diamonds;
		Card.Rank rankTest = Card.Rank.Six;

		Card testCard = new Card(suitTest, rankTest);
		
		int suitValue = testCard.getSuit().getSuitValue();
		int rankValue = testCard.getRank().getRankValue();
		
		assertEquals(2, suitValue);
		assertEquals(6, rankValue);
	}

    /**
     * Tests toString() method by constructing a string for comparison
     */
	@Test
	public void test_toString() {

		Card.Suit suitTest = Card.Suit.Clubs;
		Card.Rank rankTest = Card.Rank.Four;

		Card testCard = new Card(suitTest, rankTest);

		String rankOfSuit = rankTest.toString() + " of " + suitTest.toString();
		String testString = testCard.toString();

		assertTrue(testString.equals(rankOfSuit));
	}


}