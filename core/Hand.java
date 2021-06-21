package core;
import java.util.ArrayList;

public class Hand{

    private ArrayList<Card> hand;

    /**
     * Default constructor
     */
    public Hand()
    {
        this.hand = new ArrayList<Card>();
    }
    
    public Hand getHand()
    {
        return this; 
    }
    
    public ArrayList<Card> getHandList() {
    	return hand;
    }

    public static int  cardCount = 0; // counts the number of times a card has been drawn

    /**
     * Adds card from deck to hand
     * cardCount increases by one each time method is called
     */
    public void drawCard(Deck aDeck)
    {
        this.hand.add(aDeck.getCard(cardCount));
        cardCount++;
    }
    
    public void zeroCardCount() {
    	this.cardCount = 0;
    }

    public Card getCard(int i)
    {
        return this.hand.get(i);
    }
    
    public int getCardCount() {
    	return this.cardCount;
    }

    /**
     * Returns hand with removed card based on given index
     * @param i: index position of card that needs to be removed
     * @return hand with removed card
     */
    public Card removeCard(int i)
    {
        return this.hand.remove(i);
    }

    /**
     * Returns the number of cards in a hand
     * @return number of cards in hand
     */
    public int handSize()
    {
        return this.hand.size();
    }

    /**
     * Clears hand to allow for new cards
     */
    public void clearHand()
    {
        hand.clear();
    }

    public int handValue(Hand aHand)
    {
    	int handSize = aHand.handSize();
        int handValue = 0;
        int numOfAces = 0;

        for(int i = 0; i < handSize; i++)
        {
            if (aHand.getCard(i).getRank().toString().equals("Ace"))
            {
                numOfAces++;
            }
            else if (aHand.getCard(i).getRank().toString().equals("Jack") ||
                    aHand.getCard(i).getRank().toString().equals("Queen") ||
                    aHand.getCard(i).getRank().toString().equals("King") ||
            		aHand.getCard(i).getRank().toString().equals("Ten"))
            {
                handValue += 10;
            }
            else
            {
                handValue += (aHand.getCard(i).getRank().ordinal() + 2);
            }
        }

        if (numOfAces == 1 && handValue <= 10)
        {
            handValue += 11;
        }
        else if (numOfAces > 1 && ((handValue + 11 + numOfAces - 1) < 21))
        {
            handValue = handValue + 11 + (numOfAces - 1);
        }
        else
        {
            handValue += numOfAces;
        }

        return handValue;
    }
    public int handValueW(Hand bHand)
    {
    	int handSize = bHand.handSize();
        int handValue = 0;

        for(int i = 0; i < handSize; i++)
        {
            if (bHand.getCard(i).getRank().toString().equals("Ace"))
            {
                handValue += 14;
            }
            else if (bHand.getCard(i).getRank().toString().equals("Jack"))
            	handValue += 11;
            else if (bHand.getCard(i).getRank().toString().equals("Queen"))
            	handValue += 12;
            else if(bHand.getCard(i).getRank().toString().equals("King"))
            	handValue += 13;
            else if (bHand.getCard(i).getRank().toString().equals("Ten"))
            {
                handValue += 10;
            }
            else
            {
                handValue += (bHand.getCard(i).getRank().ordinal() + 2);
            }
        }

        return handValue;
    }


    public String toString()
    {
        String output = "";
        int i = 0;
        for(Card aCard : this.hand)
        {
            output += "\n" + aCard.toString();
            i++;
        }
        return output;
    }

}

 
