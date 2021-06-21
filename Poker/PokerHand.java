package Poker; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import core.Deck;
import core.Hand;

/**
 * Extends class Hand, adding methods that are specific to the rules of 5-draw 
 * poker, such as assigning a hand type and evaluating the differences in the 
 * case of the same hand type to determine the winner.
 */
public class PokerHand extends Hand {

	/**
	 * The hand type of the hand of cards and the numerical rank assigned
	 * to each. 
	 */
	public enum HandType 
	{
		HighCard(1), OnePair(2), TwoPairs(3), ThreeOfAKind(4), Straight(5), Flush(6), 
		FullHouse(7), FourOfAKind(8), StraightFlush(9), RoyalFlush(10); 

		private int handRank; //The numerical rank of the hand type

        HandType(int handRank)
        {
            this.handRank = handRank;
        }

        public int getHandRank()
        {
            return handRank;
        }
	}
	
	/**
	 * Returns the hand type of a hand of cards
	 * @param aHand, a poker hand to evaluate the hand type of
	 * @return currentHand, the hand type of the hand sent in
	 */
	public HandType handType(PokerHand aHand) 
	{
		HandType currentHand; // hand type to return
		Integer[] ranks = new Integer[5]; //rank values of cards in hand
		Integer[] suits = new Integer[5]; // suit values of cards in hand
		Integer[] countRanks = new Integer[5]; // stores the number of each rank
		Integer[] countSuits = new Integer[5]; // stores the number of each suit

		
		//Filling the suit/rank value arrays
		for(int i = 0; i < 5; i++)
		{
			ranks[i] = aHand.getCard(i).getRank().getRankValue();
			suits[i] = aHand.getCard(i).getSuit().getSuitValue();
		}


		int highNum = 1; // highest frequency value
		
		// Storing frequency of each card rank
		for(int i = 0; i < 5; i++) 
		{
			countRanks[i] = Collections.frequency(Arrays.asList(ranks), ranks[i]);
			countSuits[i] = Collections.frequency(Arrays.asList(suits), suits[i]);

			if(countRanks[i] > highNum)
			{ 
				highNum = countRanks[i];
			}

		}

		Arrays.sort(ranks);
		
		// Switch determines the hand type based on the highest frequency
		// of a rank as well as whether all card are of the same suit 
		// (that would mean a type of flush), and the order of card
		// ranks (exact order, one following another, indicates a type of straight)
		
		switch(highNum)

		{
			case 2: 
			
				if(Collections.frequency(Arrays.asList(countRanks), 2) == 2)
				{
					currentHand = HandType.OnePair; 

				} else currentHand = HandType.TwoPairs;

				break;

			case 3: 
			    
				if(Collections.frequency(Arrays.asList(countRanks), 2) == 2)
				{
					currentHand = HandType.FullHouse; 

				} else currentHand = HandType.ThreeOfAKind;

				break;
			

			case 4:  
			
				currentHand = HandType.FourOfAKind; //Only type with this frequency
			
				break; 

			case 1:
			
				if(countSuits[0] == 5) 
				{
					if (ranks[4] == 14 && ranks[0] == 10) 
					{
						currentHand = HandType.RoyalFlush; 

					} else if (ranks[4] == ranks[0] + 4) 
					{
						currentHand = HandType.StraightFlush;

					} else currentHand = HandType.Flush;

				} else if (ranks[4] == ranks[0] + 4) 
				{
					currentHand = HandType.Straight;

				} else currentHand = HandType.HighCard; 

				break; 

			default:

			currentHand = HandType.HighCard; // Default is the lowest hand type

			break; 
			
		}

		return currentHand; // Returns hand type

	}


    /**
     * Compares the values of card ranks for an list of hands with the same 
     * hand type
     * Assumptions: 1) because of the rarity of royal flush, it is assumed that 
	 * two players will not have this hand in the same game. 2) it is also 
	 * assumed that players won't have both matching hand types and card ranks 
	 * for all 5 cards (full tie) as this is rare as well.
	 * @param hands, the hands of players that have the same hand type
	 * @return winningHand, the hand of the player that won the round
	 */
	public PokerHand compareHandValue(ArrayList<PokerHand> hands)
	{
		int handTypeValue = handType(hands.get(0)).getHandRank(); 
		PokerHand winningHand = new PokerHand(); 

                //The cards in order of highest to lowest of the current winning hand,
                //frequencies matter, if there's three of a kind, that rank is stored
                //as highest regardless of whether there's a higher value 
				int highestCard = 0;
				int secondHighestCard = 0;
				int thirdHighestCard = 0; 
				int fourthHighestCard = 0;
				int fifthHighestCard = 0;

		switch(handTypeValue)
		{
		    // These cases are those where all card frequencies are 1 in the hand
			case 1:
			case 5:
			case 6:
			case 9:

                //Cycles through each hand, determining highest card values
                // if highest card is the same, the second highest is compared and 
                // so on until the last card is reached (purpose of else if statements)
				for(int i = 0; i < hands.size(); i++)
				{
					ArrayList<Integer> ranks = new ArrayList<Integer>();

					for(int j = 0; j < 5; j++)
					{
						int cardValue = hands.get(i).getCard(j).getRank().getRankValue();
						ranks.add(cardValue);
					}

					if (ranks.get(4) > highestCard)
					{
						winningHand = hands.get(i);

						highestCard = ranks.get(4);
						secondHighestCard = ranks.get(3);
						thirdHighestCard = ranks.get(2);
						fourthHighestCard = ranks.get(1);
						fifthHighestCard = ranks.get(0);

					} else if (ranks.get(4) == highestCard && ranks.get(3) > secondHighestCard)
					{
						winningHand = hands.get(i);

						highestCard = ranks.get(4);
						secondHighestCard = ranks.get(3);
						thirdHighestCard = ranks.get(2);
						fourthHighestCard = ranks.get(1);
						fifthHighestCard = ranks.get(0);

					} else if (ranks.get(4) == highestCard && ranks.get(3) == secondHighestCard && 
						ranks.get(2) > thirdHighestCard)
					{
						winningHand = hands.get(i);

						thirdHighestCard = ranks.get(2);
						fourthHighestCard = ranks.get(1);
						fifthHighestCard = ranks.get(0);

					} else if (ranks.get(4) == highestCard && ranks.get(3) == secondHighestCard && 
						ranks.get(2) == thirdHighestCard && ranks.get(1) > fourthHighestCard)
					{
						winningHand = hands.get(i);

						fourthHighestCard = ranks.get(1);
						fifthHighestCard = ranks.get(0);

					} else if (ranks.get(4) == highestCard && ranks.get(3) == secondHighestCard && 
						ranks.get(2) == thirdHighestCard && ranks.get(1) == fourthHighestCard &&
						ranks.get(0) > fifthHighestCard)
					{
						winningHand = hands.get(i);

						fifthHighestCard = ranks.get(0);
					}

				} 

				break; 

            //These cases are those where rank frequencies >1 are involved, ex. pairs
			case 2:
			case 3: 
			case 4:
			case 7: 
			case 8:

				int caseNumber = handTypeValue; //Used to determine frequency values to test
				
                //Cycles through each hand, checking through each card, matching
                //rank to frequency of occurence and determining wining hand accordingly
				for(int i = 0; i < hands.size(); i++)
				{
					ArrayList<Integer> ranks = new ArrayList<Integer>();

					//List makes sorting easier
					for(int j = 0; j < 5; j++)
					{
						int cardValue = hands.get(i).getCard(j).getRank().getRankValue();
						ranks.add(cardValue);
					}

					Collections.sort(ranks);

                    //If rank frequency is > 2
					if(caseNumber == 4 || caseNumber == 7 || caseNumber == 8) 
					{
						//Center card in sorted list will always belong to 4 or 3-of a kind
						if (ranks.get(2) > highestCard) 
						{
							highestCard = ranks.get(2);
							winningHand = hands.get(i);
						}
					}

					if(caseNumber == 2) //Pairs
					{
						int pairRank = 0;
						
                        //Tracks pair rank and removes the pair
						for(int j = 4; j >= 0; j--)
						{
							if(Collections.frequency(ranks, ranks.get(j)) == 2 || ranks.get(j) == pairRank)
							{
								pairRank = ranks.get(j);
								ranks.remove(j); 
							}
						}
						
                        // Pair rank is automatically highest card rank
                        // cycles through equal pair cases in else if statements,
                        // checking the array of 3 left over cards for highest ranks
						if (pairRank > highestCard)
						{
							winningHand = hands.get(i);

							highestCard = pairRank;
							secondHighestCard = ranks.get(2);
							thirdHighestCard = ranks.get(1);
							fourthHighestCard = ranks.get(0);

						} else if (pairRank == highestCard && ranks.get(2) > secondHighestCard)
						{
							winningHand = hands.get(i);

							secondHighestCard = ranks.get(2);
							thirdHighestCard = ranks.get(1);
							fourthHighestCard = ranks.get(0);

						} else if (pairRank == highestCard && ranks.get(2) == secondHighestCard && 
							ranks.get(1) > thirdHighestCard)
						{
							winningHand = hands.get(i);

							thirdHighestCard = ranks.get(1);
							fourthHighestCard = ranks.get(0);

						} else if (pairRank == highestCard && ranks.get(2) == secondHighestCard && 
							ranks.get(1) == thirdHighestCard && ranks.get(0) > fourthHighestCard)
						{
							winningHand = hands.get(i);

							fourthHighestCard = ranks.get(0);
						} 

					}
					
					if (caseNumber == 3) // two pairs
					{
					    // Spot 4/5 (index 3) guaranteed to be a card belonging
					    // to the higher ranked of the two pairs in the sorted array
					    // get(1) is guaranteed to be the rank of the lower pair, 
					    // so this is compared if higher pair is equal, otherwise 
					    // the lone card is compared when both pairs are equal between
					    // two player hands
						if (ranks.get(3) > highestCard)
						{
							highestCard = ranks.get(3);
							secondHighestCard = ranks.get(1);

							for(int j = 0; j < 5; j++ )
							{
								if(ranks.get(j) != highestCard && ranks.get(j) != secondHighestCard)
								{
									thirdHighestCard = ranks.get(j);
								}
							}

							winningHand = hands.get(i);

						} else if (ranks.get(3) == highestCard && ranks.get(1) > secondHighestCard)
						{
							secondHighestCard = ranks.get(3);

							for(int j = 0; j < 5; j++)
							{
								if(ranks.get(j) != highestCard && ranks.get(j) != secondHighestCard)
								{
									thirdHighestCard = ranks.get(j);
								}
							}

							winningHand = hands.get(i);

						} else if (ranks.get(1) == highestCard && ranks.get(3) == secondHighestCard)
						{
							for(int j = 0; j < 5; j++)
							{
								if(ranks.get(j) != highestCard && ranks.get(j) != secondHighestCard && ranks.get(j) > thirdHighestCard)
								{
									winningHand = hands.get(i);
								}
							}
						}

					}

				}

				break; 

			default:

			winningHand = hands.get(0);

				break; 
		}

		return winningHand; //returns the hand reference of the winner 
	}

}