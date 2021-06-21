package core;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    /**
     * Default constructor
     */
    public Deck()
    {
        this.deck = new ArrayList<Card>();
    }

    /**
     * Creates a new deck, all cards in standard order
     */
    public void createDeck(int numofdecks)
    {
        for(int i=0;i<numofdecks;i++) {
            for (Card.Suit suitName : Card.Suit.values()) {
                for (Card.Rank rankName : Card.Rank.values()) {
                    this.deck.add(new Card(suitName, rankName));
                }
            }
        }

    }
    
    /**
    * Resets static card counter in Hand class and clears deck
    */
    public void clearDeck() 
    {
        deck.clear();
        Hand.cardCount = 0;
    }

    /**
     * Shuffles the ordering of cards in the deck
     */
    public void shuffleDeck()
    {
        Collections.shuffle(deck);
    }

    /**
     * Retrieves card from given index
     * @param i: index position
     * @return card located in given index position
     */
    public Card getCard(int i)
    {
        return this.deck.get(i);
    }

    public String toString()
    {
        String output = "";
        int i = 0;
        for(Card aCard : this.deck)
        {
            output += "\n" + aCard.toString();
            i++;
        }
        return output;
    }

}
