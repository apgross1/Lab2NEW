package pokerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;

public class Hand {
	private UUID playerID;
	@XmlElement
	private ArrayList<Card> CardsInHand;
	private ArrayList<Card> BestCardsInHand;

	@XmlElement
	private int HandStrength;
	@XmlElement
	private int HiHand;
	@XmlElement
	private int LoHand;
	@XmlElement
	private ArrayList<Card> Kicker; //ArrayList of remaining cards in hand

	private boolean bScored = false;

	private boolean Flush; //Value determined by whether or not the hand contains a Flush
	private boolean Straight; //Value determined by whether or not the hand contains a Straight
	
	/**
	 * Getter for Straight; holds value for Straight attribute
	 * @return
	 */
	private boolean isStraight() {
		return Straight;
	}
	
	/**
	 * Setter for Straight
	 * @param straight
	 */
	private void setStraight(boolean straight) {
		Straight = straight;
	}

	private boolean StraightFlush; //Value determined by whether or not the hand contains a Straight Flush
	private boolean Ace; //Value determined by whether or not the hand contains an Ace
	private static Deck dJoker = new Deck(); //Value determined by whether or not the deck contains a joker 
	private boolean FiveOfKind; //Value determined by whether or not the hand contains a five of a kind
	private boolean FourofKind; //Value determined by whether or not the hand contains a four of a kind
	private boolean ThreeofKind; //Value determined by whether or not the hand contains a three of a kind
	private boolean Pair; //Value determined by whether or not the hand contains a pair
	private boolean RoyalFlush; //Value determined by whether or not the hand contains a royal flush
	private boolean TwoPair; //Value determined by whether or not the hand contains two pairs
	private boolean FullHouse;
	

	/**
	 * Getter for FullHouse; returns its boolean value
	 * @return
	 */
	private boolean isFullHouse() {
		return FullHouse;
	}
	
	/**
	 * Setter for FullHouse
	 * @param fullHouse
	 */
	private void setFullHouse(boolean fullHouse) {
		FullHouse = fullHouse;
	}
	
	/**
	 * Getter for Three of a Kind boolean value
	 * @return
	 */
	private boolean isThreeofKind() {
		return ThreeofKind;
	}
	
	/**
	 * Setter for Three of a Kind value
	 * @param threeofKind
	 */
	private void setThreeofKind(boolean threeofKind) {
		ThreeofKind = threeofKind;
	}

	/**
	 * Setter for four of a kind attribute
	 * @param fourofKind
	 */
	private void setFourofKind(boolean fourofKind) {
		FourofKind = fourofKind;
	}
	
	/**
	 * Default constructor for Hand class
	 */
	public Hand()
	{
		
	}
	
	/**
	 * Adds a card to the player's hand. If there are no cards in the hand the method will create a new CardsInHand attribute.
	 * @param c
	 */
	public void  AddCardToHand(Card c)
	{
		if (this.CardsInHand == null)
		{
			CardsInHand = new ArrayList<Card>();
		}
		this.CardsInHand.add(c);
	}
	
	
	/**
	 * Takes a card from the player's hand using its index location in CardsInHand arrayList
	 * @param location
	 * @return
	 */
	public Card GetCardFromHand(int location)
	{
		return CardsInHand.get(location);
	}
	
	/**
	 * Creates new hand by drawing from deck
	 * @param d
	 */
	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;
	}

	/**
	 * Creates a hand based off of predefined ArrayList of Card instances
	 * @param setCards
	 */
	public Hand(ArrayList<Card> setCards) {
		this.CardsInHand = setCards;
	}

	/**
	 * Getter for CardsInHand attribute; returns ArrayList of Card instances in player's hand
	 * @return
	 */
	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	/**
	 * Getter for BestCardsInHand
	 * @return
	 */
	public ArrayList<Card> getBestHand() {
		return BestCardsInHand;
	}

	/**
	 * Setter for player's unique ID
	 * @param playerID
	 */
	public void setPlayerID(UUID playerID)
	{
		this.playerID = playerID;
	}
	
	/**
	 * Getter for unique player ID
	 * @return
	 */
	public UUID getPlayerID()
	{
		return playerID;
	}
	
	/**
	 * Setter for BestCardsInHand
	 * @param BestHand
	 */
	public void setBestHand(ArrayList<Card> BestHand) {
		this.BestCardsInHand = BestHand;
	}

	/**
	 * Getter for the hand strength of the player's hand based on predefined values in eHandStrength enumerator class
	 * @return
	 */
	public int getHandStrength() {
		return HandStrength;
	}


	/**
	 * Setter for the Kicker ArrayList
	 * @param arrayList
	 */
	public void setKicker(ArrayList<Card> arrayList) {
		this.Kicker = arrayList;
		Collections.sort(this.Kicker, Card.CardRank);
	}
	
	/**
	 * Adds kickers (instance of Card) to a pre-existing Kicker ArrayList and sorts the list
	 * @param card
	 */
	public void addKicker(Card card) {
		this.Kicker.add(card);
		Collections.sort(this.Kicker, Card.CardRank);
	}
	
	/**
	 * Getter for Kicker attribute and returns an ArrayList of Card instances
	 * @return
	 */
	public ArrayList<Card> getKicker() {
		return Kicker;
	}

	/**
	 * Returns strength of the high pair in a hand
	 * @return
	 */
	public int getHighPairStrength() {
		return HiHand;
	}

	/**
	 * Returns strength of the low pair in a hand
	 * @return
	 */
	public int getLowPairStrength() {
		return LoHand;
	}

	/**
	 * Getter for boolean that determines whether or not there is an ace in the hand
	 * @return
	 */
	public boolean getAce() {
		return Ace;
	}

	/**
	 * Creates hand with seeded values
	 * @param SeededHand
	 * @return
	 */
	public static Hand EvalHand(ArrayList<Card> SeededHand) {
		
		Deck d = new Deck();
		Hand h = new Hand(d);
		h.CardsInHand = SeededHand;

		return h;
	}
	
	/**
	 * Evaluates the player's hand to determine its value
	 */
	public void EvalHand() {
		// Sort the cards!
		Collections.sort(this.getCards(), Card.CardRank);

		// Ace Evaluation
		if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			Ace = true;
		}

		// Flush Evaluation
		if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getSuit() == this.getCards()
				.get(eCardNo.SecondCard.getCardNo()).getSuit()
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getSuit() == this.getCards()
						.get(eCardNo.ThirdCard.getCardNo()).getSuit()
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getSuit() == this.getCards()
						.get(eCardNo.FourthCard.getCardNo()).getSuit()
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getSuit() == this.getCards()
						.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
			Flush = true;
		} else {
			Flush = false;
		}

		// five of a Kind
		if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.FiveOfAKind,
					this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, this.getKicker());
			this.setFiveOfKind(true);
		}
		
		// Four of a kind
		else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == this.getCards()
				.get(eCardNo.FourthCard.getCardNo()).getRank().getRank() || this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank().getRank() == this.getCards()
				.get(eCardNo.FifthCard.getCardNo()).getRank().getRank()) {
				if (this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank().getRank() != this.getCards()
						.get(eCardNo.FourthCard.getCardNo()).getRank().getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					int hiFour = 0;
					
					if (this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank().getRank() > this.getCards()
						.get(eCardNo.FourthCard.getCardNo()).getRank().getRank()){
						hiFour = this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank().getRank();
					}
					ScoreHand(eHandStrength.FourOfAKind, hiFour, 0, this.getKicker());
					this.setFourofKind(true);
				}
				else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() != this.getCards()
						.get(eCardNo.SecondCard.getCardNo()).getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					int hiFour = 0;
					if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank() > this.getCards()
							.get(eCardNo.SecondCard.getCardNo()).getRank().getRank()){
							hiFour = this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
						}
					ScoreHand(eHandStrength.FourOfAKind, hiFour, 0, this.getKicker());
					this.setFourofKind(true);
				}
			}
		
		//Three of a kind
		else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.ThirdCard.getCardNo()).getRank() || this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.FourthCard.getCardNo()).getRank() || this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank()) {
				
				this.setThreeofKind(true);
				if (this.isFullHouse() == false) {	
					int hiCardThreeKind = 0;
					for (int i = 0; i < this.getCards().size(); i++) {
						if (this.getCards().get(i).getRank().getRank() > hiCardThreeKind) {
							hiCardThreeKind = this.getCards().get(i).getRank().getRank();
						}
						else
							continue;
					}
					
					if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank()) {
						ArrayList<Card> kicker = new ArrayList<Card>();
						this.setKicker(kicker);
						for (int i = 3; i < this.getCards().size(); i++ ) {
							this.addKicker(this.getCards().get(i));
						}
						
					}
					
					else if (this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank()) {
						ArrayList<Card> kicker = new ArrayList<Card>();
						this.setKicker(kicker);
						this.addKicker(this.getCards().get(0));
						this.addKicker(this.getCards().get(4));
					}
					
					else if (this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank()) {
						ArrayList<Card> kicker = new ArrayList<Card>();
						this.setKicker(kicker);
						this.addKicker(this.getCards().get(0));
						this.addKicker(this.getCards().get(1));
						
					}
					ScoreHand(eHandStrength.ThreeOfAKind,hiCardThreeKind,0, this.getKicker());
				}
		}
		
		//Pair and 2-Pair
		//Pair
		else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.SecondCard.getCardNo()).getRank() || this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.ThirdCard.getCardNo()).getRank() || this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.FourthCard.getCardNo()).getRank() || this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ArrayList<Card> pairHand = new ArrayList<Card>();
			
			if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank()) {
				pairHand.add(this.getCards().get(eCardNo.FirstCard.getCardNo()));
				pairHand.add(this.getCards().get(eCardNo.SecondCard.getCardNo()));	
			}
			
			if (this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				{
					pairHand.add(this.getCards().get(eCardNo.SecondCard.getCardNo()));
					pairHand.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
				}
			}
			
			if (this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank()) {
				pairHand.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
				pairHand.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
				}
			
			if (this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank()) {
				
					pairHand.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
					pairHand.add(this.getCards().get(eCardNo.FifthCard.getCardNo()));
			}
			
			//Test to see if it is a 2-Pair hand
			if (pairHand.size() == 4) { //Testing to see if there are two pairs in the pair ArrayList
				int highcard;
				int lowcard;
				if (pairHand.get(0).getRank().getRank() > pairHand.get(2).getRank().getRank()) {
					highcard = pairHand.get(0).getRank().getRank();
					lowcard = pairHand.get(2).getRank().getRank();
				} 
				else {
					highcard = pairHand.get(2).getRank().getRank();
					lowcard = pairHand.get(0).getRank().getRank();
				}
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				for (int i = 0; i < this.getCards().size(); i ++) {
					for (int j = 0; j < pairHand.size(); j ++) {
						if (this.getCards().get(i).getRank().getRank() == pairHand.get(j).getRank().getRank()) {
							continue;
						}
						else
							this.addKicker(this.getCards().get(i));
					}
				}
				this.setTwoPair(true);
				ScoreHand(eHandStrength.TwoPair, highcard, lowcard, this.getKicker());
		
			}
			
			else if (pairHand.size() == 2) { //If the hand does not have two pairs, the program checks if there is one pair
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				for (int i = 0; i < this.getCards().size(); i ++) {
					for (int j = 0; j < pairHand.size(); j ++) {
						if (this.getCards().get(i).getRank().getRank() == pairHand.get(j).getRank().getRank()) {
							continue;
						}
						else
							this.addKicker(this.getCards().get(i));
					}
				}
				int hiCardPair = 0;
				for (int i = 0; i < this.getCards().size(); i++) {
					if (this.getCards().get(i).getRank().getRank() > hiCardPair) {
						hiCardPair = this.getCards().get(i).getRank().getRank();
					}
					else
						continue;
				}
				ScoreHand(eHandStrength.Pair, hiCardPair, 0, this.getKicker());
			}
			else
				this.setTwoPair(false);
			}
		
		//If the hand does not contain any of the hands above, its value will be equal to the highest ranking card in the hand
		//This is tested below
		else {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < this.getCards().size(); i++ ) {
				this.addKicker(this.getCards().get(i));
			}
			ScoreHand(eHandStrength.HighCard,
					this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					this.getKicker());
		}
		
		// Straight Evaluation
		if (Ace) {
			// Looks for Ace, King, Queen, Jack, 10
			if (this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
					&& this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
					&& this.getCards().get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.JACK
					&& this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
				this.setStraight(true);
			// Looks for Ace, 2, 3, 4, 5
			} else if (this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& this.getCards().get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& this.getCards().get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.FIVE) {
				this.setStraight(true);
			} else {
				this.setStraight(false);
			}
		// Looks for straight without Ace
		} else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
				.getRank() == this.getCards().get(eCardNo.SecondCard.getCardNo())
				.getRank().getRank() + 1
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == this.getCards()
						.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank() + 2
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == this.getCards()
						.get(eCardNo.FourthCard.getCardNo()).getRank()
						.getRank() + 3
				&& this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == this.getCards()
						.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() + 4) {
			this.setStraight(true);
		} else {
			this.setStraight(false);
		}

		// Evaluate Royal Flush
		if (this.isStraight()
				&& Flush == true
				&& this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, this.getKicker());
			this.setRoyalFlush(true);
			this.setStraightFlush(false);
		}

		// Straight Flush
		else if (this.isStraight() && Flush == true) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.StraightFlush,
					this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, this.getKicker());
			setStraightFlush(true);
			this.setRoyalFlush(false);
		}
		
		//Normal straight
		else if (Flush == false & this.isStraight() == true) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.Straight, this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}
		
		//Flush
		else if (Flush == true & this.isRoyalFlush() == false & this.isStraightFlush() == false) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < this.getCards().size(); i++) {
				this.addKicker(this.getCards().get(i));
			}
			ScoreHand(eHandStrength.Flush, this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}

		// Full House
		if (this.isThreeofKind() == true) {
			ArrayList<Card> copyHand; //Creates copy of hand to manipulate
			copyHand = this.getCards();
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(0);
				}
			}
			
			
			else if (copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == copyHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(2);
				}
			}
			
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				this.setFullHouse(true);
				ScoreHand(eHandStrength.FullHouse, eHandStrength.ThreeOfAKind.getHandStrength(), eHandStrength.Pair.getHandStrength(), this.getKicker());
		}
	}
}
	

	/**
	 * Getter that returns boolean value stored inside FiveOfKind
	 * @return
	 */
	private boolean isFiveOfKind() {
		return FiveOfKind;
	}
	
	/**
	 * Setter that determines the boolean value for FiveOfKind
	 * @param fiveOfKind
	 */
	private void setFiveOfKind(boolean fiveOfKind) {
		FiveOfKind = fiveOfKind;
	}
	
	
	/**
	 * Setter that sets score values of the hand
	 * @param hST
	 * @param HiHand
	 * @param LoHand
	 * @param Kicker
	 */
	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, ArrayList<Card> Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	/**
	 * Getter that returns boolean value of StraightFlush to determine whether hand has Straight Flush
	 * @return
	 */
	public boolean isStraightFlush() {
		return StraightFlush;
	}
	
	/**
	 * Setter that assigns boolean value to StraightFlush
	 * @param straightFlush
	 */
	public void setStraightFlush(boolean straightFlush) {
		StraightFlush = straightFlush;
	}

	/**
	 * Getter that return boolean value of RoyalFlush to determine whether hand has Royal Flush
	 * @return
	 */
	public boolean isRoyalFlush() {
		return RoyalFlush;
	}
	
	/**
	 * Setter that assigns boolean value to RoyalFlush
	 * @param royalFlush
	 */
	public void setRoyalFlush(boolean royalFlush) {
		RoyalFlush = royalFlush;
	}

	/**
	 * Getter that returns boolean value of TwoPair to determine whether hand has Two Pair
	 * @return
	 */
	public boolean isTwoPair() {
		return TwoPair;
	}
	
	/**
	 * Setter that assigns boolean value to RoyalFlush
	 * @param twoPair
	 */
	public void setTwoPair(boolean twoPair) {
		TwoPair = twoPair;
	}

	/**
	 * Custom sort to figure the best hand in an array of hands
	 * A positive return value indicates Hand 2 is a better hand than Hand 1. A negative value indicates Hand 1 is a better hand than Hand 2.
	 */
	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandStrength() - h1.getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHighPairStrength() - h1.getHighPairStrength();
			if (result != 0) {
				return result;
			}

			result = h2.getLowPairStrength() - h1.getLowPairStrength();
			if (result != 0) {
				return result;
			}

			
			//Compares hands based on their kickers
			for (int i = 0; i < h1.getKicker().size(); i++) {
				for (int j = 0; j < h2.getKicker().size(); j ++) {
					if (h1.getKicker().get(i).getRank().getRank() > h2.getKicker().get(j).getRank().getRank() || h2.getKicker().get(j).getRank().getRank() > h1.getKicker().get(i).getRank().getRank()) {
						return (h1.getKicker().get(i).getRank().getRank() - h2.getKicker().get(j).getRank().getRank());
					}
					
					else if (h1.getKicker().get(i).getRank().getRank() == h2.getKicker().get(j).getRank().getRank())
						continue;
				}
			}
			return result;
		}
	};
}
