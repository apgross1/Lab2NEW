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
	private int Kicker;

	private boolean bScored = false;

	private boolean Flush;
	private boolean Straight;
	private boolean StraightFlush;
	private boolean Ace;
	private static Deck dJoker = new Deck();
	private boolean FiveOfKind;
	private boolean FourofKind;
	private boolean ThreeofKind;
	private boolean Pair;
	private boolean RoyalFlush;

	private boolean isThreeofKind() {
		return ThreeofKind;
	}
	private void setThreeofKind(boolean threeofKind) {
		ThreeofKind = threeofKind;
	}
	private boolean isPair() {
		return Pair;
	}
	private void setPair(boolean pair) {
		Pair = pair;
	}
	private boolean isFourofKind() {
		return FourofKind;
	}
	private void setFourofKind(boolean fourofKind) {
		FourofKind = fourofKind;
	}
	public Hand()
	{
		
	}
	public void  AddCardToHand(Card c)
	{
		if (this.CardsInHand == null)
		{
			CardsInHand = new ArrayList<Card>();
		}
		this.CardsInHand.add(c);
	}
	
	public Card  GetCardFromHand(int location)
	{
		return CardsInHand.get(location);
	}
	
	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;


	}


	public Hand(ArrayList<Card> setCards) {
		this.CardsInHand = setCards;
	}

	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	public ArrayList<Card> getBestHand() {
		return BestCardsInHand;
	}

	public void setPlayerID(UUID playerID)
	{
		this.playerID = playerID;
	}
	public UUID getPlayerID()
	{
		return playerID;
	}
	public void setBestHand(ArrayList<Card> BestHand) {
		this.BestCardsInHand = BestHand;
	}

	public int getHandStrength() {
		return HandStrength;
	}


	public int getKicker() {
		return Kicker;
	}

	public int getHighPairStrength() {
		return HiHand;
	}

	public int getLowPairStrength() {
		return LoHand;
	}

	public boolean getAce() {
		return Ace;
	}

	public static Hand EvalHand(ArrayList<Card> SeededHand) {
		
		Deck d = new Deck();
		Hand h = new Hand(d);
		h.CardsInHand = SeededHand;

		return h;
	}

	public void EvalHand() {
		// Evaluates if the hand is a flush and/or straight then figures out
		// the hand's strength attributes

		// Sort the cards!
		Collections.sort(CardsInHand, Card.CardRank);

		// Ace Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			Ace = true;
		}

		// Flush Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
			Flush = true;
		} else {
			Flush = false;
		}

		// five of a Kind

		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FiveOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
			this.setFiveOfKind(true);
		}
		else
			this.setFiveOfKind(false);
		
		

		// Straight Evaluation
		if (Ace) {
			// Looks for Ace, King, Queen, Jack, 10
			if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.JACK
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
				Straight = true;
				// Looks for Ace, 2, 3, 4, 5
			} else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.FIVE) {
				Straight = true;
			} else {
				Straight = false;
			}
			// Looks for straight without Ace
		} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
				.getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo())
				.getRank().getRank() + 1
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank() + 2
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
						.getRank() + 3
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() + 4) {
			Straight = true;
		} else {
			Straight = false;
		}

		// Evaluate Royal Flush
		if (Straight == true
				&& Flush == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
			this.setRoyalFlush(true);
			this.setStraightFlush(false);
		}

		// Straight Flush
		else if (Straight == true && Flush == true) {
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
			setStraightFlush(true);
			this.setRoyalFlush(false);
		}
		// Four of a Kind
		if (this.isFiveOfKind() == false) {
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.FourthCard.getCardNo()).getRank() || CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				
				if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
					int kicker = CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank();
					ScoreHand(eHandStrength.FourOfAKind, 0, 0, kicker);
					this.setFourofKind(true);
				}
				else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.SecondCard.getCardNo()).getRank()) {
					int kicker = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
					ScoreHand(eHandStrength.FourOfAKind, 0, 0, kicker);
					this.setFourofKind(true);
				}
			}
			else
				this.setFourofKind(false);
		}

		// Full House
		if (this.isThreeofKind() == true) {
			ArrayList<Card> copyHand;
			copyHand = CardsInHand;
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(0);
				}
			}
			
			else if (copyHand.get(eCardNo.SecondCard.getCardNo()).getRank() == copyHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(1);
				}
			}
			
			else if (copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == copyHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(2);
				}
			}
			
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				ScoreHand(eHandStrength.FullHouse, eHandStrength.ThreeOfAKind.getHandStrength(), eHandStrength.Pair.getHandStrength(), 0);
		}

		// Flush
		if (Flush == true & this.isRoyalFlush() == false & this.isStraightFlush() == false) {
			ScoreHand(eHandStrength.Flush, 0, 0, 0);
		}
		
		// Straight
		if (Straight == true & this.isStraightFlush() == false) {
			ScoreHand(eHandStrength.Straight, 0, 0, 0);
		}

		// Three of a Kind
		if (this.isFourofKind() == false) {
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.ThirdCard.getCardNo()).getRank() || CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank() || CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				//FILL IN PARAMETERS HERE!
				ScoreHand(eHandStrength.ThreeOfAKind,0,0,0);
			}
			else
				this.setThreeofKind(false);
		}
		
		// Two Pair
			if (Pair == true) {
				ArrayList<Card> pairHand = new ArrayList<Card>();
				
				if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
					pairHand.add(CardsInHand.get(eCardNo.FirstCard.getCardNo()));
					pairHand.add(CardsInHand.get(eCardNo.SecondCard.getCardNo()));	
				}
				
				else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
					{
						pairHand.add(CardsInHand.get(eCardNo.SecondCard.getCardNo()));
						pairHand.add(CardsInHand.get(eCardNo.ThirdCard.getCardNo()));
					}
				}
				
				else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
					pairHand.add(CardsInHand.get(eCardNo.FourthCard.getCardNo()));
					pairHand.add(CardsInHand.get(eCardNo.ThirdCard.getCardNo()));
					}
				
				else if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
					
						pairHand.add(CardsInHand.get(eCardNo.FourthCard.getCardNo()));
						pairHand.add(CardsInHand.get(eCardNo.FifthCard.getCardNo()));
				}
				
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
				int kicker = 0;
				for (int i = 0; i < CardsInHand.size(); i ++) {
					for (int j = 0; j < pairHand.size(); j ++) {
						if (CardsInHand.get(i).getRank().getRank() == pairHand.get(j).getRank().getRank()) {
							continue;
						}
						else
							kicker = CardsInHand.get(i).getRank().getRank();
					}
				}
				ScoreHand(eHandStrength.TwoPair, highcard, lowcard, kicker);
			}
		

		// Pair
		if (this.isThreeofKind() == false) {
			ArrayList<Card> copyPair = new ArrayList<Card>();
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				copyPair.add(CardsInHand.get(eCardNo.FirstCard.getCardNo()));
				copyPair.add(CardsInHand.get(eCardNo.FourthCard.getCardNo()));
			}
			
			else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				copyPair.add(CardsInHand.get(eCardNo.SecondCard.getCardNo()));
				copyPair.add(CardsInHand.get(eCardNo.ThirdCard.getCardNo()));
			}
			
			else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				copyPair.add(CardsInHand.get(eCardNo.ThirdCard.getCardNo()));
				copyPair.add(CardsInHand.get(eCardNo.FourthCard.getCardNo()));
			}
			
			else if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				copyPair.add(CardsInHand.get(eCardNo.FourthCard.getCardNo()));
				copyPair.add(CardsInHand.get(eCardNo.FifthCard.getCardNo()));
			}
			
			int kicker = 0;
			for (int i = 0; i < CardsInHand.size(); i ++) {
				for (int j = 0; j < copyPair.size(); j ++) {
					if (CardsInHand.get(i).getRank().getRank() == copyPair.get(j).getRank().getRank()) {
						continue;
					}
					else
						kicker += CardsInHand.get(i).getRank().getRank();
				}
			}
			ScoreHand(eHandStrength.TwoPair, 0, 0, kicker);
			
		}
		
		

		// High Card
		//	I'll give you this one :)
		else {
			ScoreHand(eHandStrength.HighCard,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank());
			}	
		}
	}

	private boolean isFiveOfKind() {
		return FiveOfKind;
	}
	private void setFiveOfKind(boolean fiveOfKind) {
		FiveOfKind = fiveOfKind;
	}
	
	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, int Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	public boolean isStraightFlush() {
		return StraightFlush;
	}
	public void setStraightFlush(boolean straightFlush) {
		StraightFlush = straightFlush;
	}

	public boolean isRoyalFlush() {
		return RoyalFlush;
	}
	public void setRoyalFlush(boolean royalFlush) {
		RoyalFlush = royalFlush;
	}

	/**
	 * Custom sort to figure the best hand in an array of hands
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

			result = h2.getKicker() - h1.getKicker();
			if (result != 0) {
				return result;
			}

			return 0;
		}
	};
}
