package core;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

    /**
     * Junit tests for Hand class method functionality. 
     */

	public class Hand_Test {

		Deck deckTest = new Deck(); //Universal testing deck

		
		/**
		 * Tests drawing and getting methods, the latter is compared to
		 * deck class, which can be used as it is also tested in the CoreTestSuite 
		 * i.e. its problems will be picked out sperarately
		 */
		@Test
		public void test_DrawCard_GetCard() {

			Hand handTest = new Hand();

			deckTest.createDeck(1);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			assertEquals(2, Hand.cardCount);
			assertEquals(deckTest.getCard(0), handTest.getCard(0));
			assertEquals(deckTest.getCard(1), handTest.getCard(1));

			deckTest.clearDeck(); //Reset Hand.cardCount, deck
		}

        /**
         * Testing that when a card is removed, the next card index moves back one
         */
		@Test
		public void test_RemoveCard() {
			
			Hand handTest = new Hand();

			deckTest.createDeck(1);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			handTest.removeCard(0);
			
			assertEquals(deckTest.getCard(1), handTest.getCard(0));

			deckTest.clearDeck(); //Reset Hand.cardCount, deck
		}

        /**
         * Handsize should return number of cards drawn, and this should be 0
         * at the start or after clearing the hand
         */
		@Test
		public void test_HandSize_clearHand() {
			
			Hand handTest = new Hand();

			deckTest.createDeck(1);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			
			assertEquals(3, handTest.handSize());

			handTest.clearHand();

			assertEquals(0, handTest.handSize());

			deckTest.clearDeck(); //Reset Hand.cardCount, deck

		}
		
		/** 
		 * Testing hand value method called in Blackjack using unsorted deck to predict card value
		 */

		@Test
		public void test_HandValue() {

			int handValue = 0;

			Hand handTest = new Hand();

			deckTest.createDeck(1);

			Hand.cardCount = 9; //Unsorted, so can predict cards that will be drawn from here

			//Should draw Clubs- Jack, Queen, King
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			assertEquals(10*3, handTest.handValue(handTest)); // king,queen,jack are all value 10

			handTest.removeCard(2);
			handTest.drawCard(deckTest); // Should draw Clubs-Ace

			assertEquals(10*2+1, handTest.handValue(handTest)); //Ace treated as 1

			handTest.removeCard(0);
			handTest.removeCard(0);

			assertEquals(11, handTest.handValue(handTest)); // Ace treated as 11

			// Should draw Diamonds- Two, Three
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			Hand.cardCount += 10; //Sets up to draw another ace

			handTest.drawCard(deckTest); // Should draw diamonds-Ace

			assertEquals(2+3+11+1, handTest.handValue(handTest)); //Second ace should be treated as 1

			deckTest.clearDeck(); //Reset Hand.cardCount, deck
		}

	    /**
	     * Testing hand value method called in War using unsorted deck to predict card value
	     */
	
		@Test
		public void test_HandValueW() {

			int handValue = 0;

			Hand handTest = new Hand();

			deckTest.createDeck(1);

			Hand.cardCount = 9; //Unsorted, so can predict cards that will be drawn from here

			//Should draw Clubs- Jack, Queen, King
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			assertEquals(11+12+13, handTest.handValueW(handTest));

			handTest.removeCard(2);
			handTest.drawCard(deckTest); // Should draw Clubs-Ace

			assertEquals(11+12+14, handTest.handValueW(handTest)); 

			handTest.removeCard(0);
			handTest.removeCard(0);
			handTest.removeCard(0);

			// Should draw Diamonds- Two, Three, four
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			assertEquals(2 + 3 + 4, handTest.handValueW(handTest));

			deckTest.clearDeck(); //Reset Hand.cardCount, deck

		}
        
        /**
         * Testing toString method by building string and comparing
         * to what is returned by the method
         */

		@Test
		public void test_toString() {

			String cards = "";

			Hand handTest = new Hand();

			deckTest.createDeck(1);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);
            handTest.drawCard(deckTest);
			handTest.drawCard(deckTest);

			for(int i = 0; i < 5; i++){

				cards += "\n" + handTest.getCard(i).toString();				
			}

			assertTrue(cards.equals(handTest.toString()));

			deckTest.clearDeck(); //Reset Hand.cardCount, deck
		}

	}