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
	private ArrayList<Card> Kicker;

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


	public void setKicker(ArrayList<Card> arrayList) {
		this.Kicker = arrayList;
		Collections.sort(this.Kicker, Card.CardRank);
	}
	
	public void addKicker(Card card) {
		this.Kicker.add(card);
		Collections.sort(this.Kicker, Card.CardRank);
	}
	
	public ArrayList<Card> getKicker() {
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
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.FiveOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, this.getKicker());
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
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, this.getKicker());
			this.setRoyalFlush(true);
			this.setStraightFlush(false);
		}

		// Straight Flush
		else if (Straight == true && Flush == true) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, this.getKicker());
			setStraightFlush(true);
			this.setRoyalFlush(false);
		}
		// Four of a Kind
		if (this.isFiveOfKind() == false) {
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() == CardsInHand
					.get(eCardNo.FourthCard.getCardNo()).getRank().getRank() || CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank().getRank()) {
				
				if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() != CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank().getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					int hiFour = 0;
					
					if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() > CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank().getRank()){
						hiFour = CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank();
					}
					
					ScoreHand(eHandStrength.FourOfAKind, hiFour, 0, this.getKicker());
					this.setFourofKind(true);
				}
				else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.SecondCard.getCardNo()).getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					int hiFour = 0;
					if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank() > CardsInHand
							.get(eCardNo.SecondCard.getCardNo()).getRank().getRank()){
							hiFour = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
						}
					
					ScoreHand(eHandStrength.FourOfAKind, hiFour, 0, this.getKicker());
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
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				ScoreHand(eHandStrength.FullHouse, eHandStrength.ThreeOfAKind.getHandStrength(), eHandStrength.Pair.getHandStrength(), this.getKicker());
		}

		// Flush
		if (Flush == true & this.isRoyalFlush() == false & this.isStraightFlush() == false) {
			int hiCardFlush = 0;
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < CardsInHand.size(); i++) {
				this.addKicker(CardsInHand.get(i));
			}
			ScoreHand(eHandStrength.Flush, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}
		
		// Straight
		if (Straight == true & this.isStraightFlush() == false) {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.Straight, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}

		// Three of a Kind
		if (this.isFourofKind() == false) {
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.ThirdCard.getCardNo()).getRank() || CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank() || CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				int hiCardThreeKind = 0;
				for (int i = 0; i < CardsInHand.size(); i++) {
					if (CardsInHand.get(i).getRank().getRank() > hiCardThreeKind) {
						hiCardThreeKind = CardsInHand.get(i).getRank().getRank();
					}
					else
						continue;
				}
				
				if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					for (int i = 3; i < CardsInHand.size(); i++ ) {
						this.addKicker(CardsInHand.get(i));
					}
					
				}
				
				else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					this.addKicker(CardsInHand.get(0));
					this.addKicker(CardsInHand.get(4));
				}
				
				else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
					ArrayList<Card> kicker = new ArrayList<Card>();
					this.setKicker(kicker);
					this.addKicker(CardsInHand.get(0));
					this.addKicker(CardsInHand.get(1));
					
				}
				ScoreHand(eHandStrength.ThreeOfAKind,hiCardThreeKind,0, this.getKicker());
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
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				for (int i = 0; i < CardsInHand.size(); i ++) {
					for (int j = 0; j < pairHand.size(); j ++) {
						if (CardsInHand.get(i).getRank().getRank() == pairHand.get(j).getRank().getRank()) {
							continue;
						}
						else
							this.addKicker(CardsInHand.get(i));
					}
				}
				ScoreHand(eHandStrength.TwoPair, highcard, lowcard, this.getKicker());
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
			
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 0; i < CardsInHand.size(); i ++) {
				for (int j = 0; j < copyPair.size(); j ++) {
					if (CardsInHand.get(i).getRank().getRank() == copyPair.get(j).getRank().getRank()) {
						continue;
					}
					else
						this.addKicker(CardsInHand.get(i));
				}
			}
			int hiCardPair = 0;
			for (int i = 0; i < CardsInHand.size(); i++) {
				if (CardsInHand.get(i).getRank().getRank() > hiCardPair) {
					hiCardPair = CardsInHand.get(i).getRank().getRank();
				}
				else
					continue;
			}
			ScoreHand(eHandStrength.Pair, hiCardPair, 0, this.getKicker());
			
		}
		
		

		// High Card
		//	I'll give you this one :)
		else {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < CardsInHand.size(); i++ ) {
				this.addKicker(CardsInHand.get(i));
			}
			ScoreHand(eHandStrength.HighCard,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					this.getKicker());
			}	
		}
	}

	private boolean isFiveOfKind() {
		return FiveOfKind;
	}
	private void setFiveOfKind(boolean fiveOfKind) {
		FiveOfKind = fiveOfKind;
	}
	
	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, ArrayList<Card> Kicker) {
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
