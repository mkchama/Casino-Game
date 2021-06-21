package core;
public class Card {

    public enum Suit
    {
        Clubs(1), Diamonds(2), Hearts(3), Spades(4);

        private int suitValue;

        Suit(int suitValue)
        {
            this.suitValue = suitValue;
        }

        public int getSuitValue()
        {
            return suitValue;
        }
    }

    public enum Rank
    {
        Two(2), Three(3), Four(4), Five(5),
        Six(6), Seven(7), Eight(8), Nine(9), Ten(10),
        Jack(11), Queen(12), King(13), Ace(14);

        private int rankValue;

        Rank(int rankValue)
        {
            this.rankValue = rankValue;
        }

        public int getRankValue()
        {
            return rankValue;
        }
    }

    protected Suit suit;
    protected Rank rank;
    

    public Card(Suit suit, Rank rank)
    {
        this.suit = suit;
        this.rank = rank;
    }

    public Rank getRank()
    {
        return rank;
    }
    
    public Suit getSuit() {
    	
    	return suit;
    }

    public String toString()
    {
        return this.rank.toString() + " of " + this.suit.toString();
    }

}
