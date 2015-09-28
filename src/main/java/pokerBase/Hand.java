package pokerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.util.SystemPropertyUtils;

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
	
	private boolean isStraight() {
		return Straight;
	}
	private void setStraight(boolean straight) {
		Straight = straight;
	}

	private boolean StraightFlush;
	private boolean Ace;
	private static Deck dJoker = new Deck();
	private boolean FiveOfKind;
	private boolean FourofKind;
	private boolean ThreeofKind;
	private boolean Pair;
	private boolean RoyalFlush;
	private boolean TwoPair;
	private boolean FullHouse;
	

	private boolean isFullHouse() {
		return FullHouse;
	}
	private void setFullHouse(boolean fullHouse) {
		FullHouse = fullHouse;
	}
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
		Collections.sort(this.getCards(), Card.CardRank);

		// Ace Evaluation
		if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			System.out.println("There's an ace in here");
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
			System.out.println("Is flush");
			Flush = true;
		} else {
			Flush = false;
		}

		// five of a Kind

		if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			System.out.println("I went through the five of a kind test");
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
					System.out.println("Is four of kind");
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
					System.out.println("Is four of kind");
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
		//Pair copy
		//Pair
		/**else if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.SecondCard.getCardNo()).getRank() || this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.ThirdCard.getCardNo()).getRank() || this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.FourthCard.getCardNo()).getRank() || this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ArrayList<Card> copyPair = new ArrayList<Card>();
			if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				copyPair.add(this.getCards().get(eCardNo.FirstCard.getCardNo()));
				copyPair.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
			}
			
			else if (this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards()
					.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				copyPair.add(this.getCards().get(eCardNo.SecondCard.getCardNo()));
				copyPair.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
			}
			
			else if (this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank()) {
				copyPair.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
				copyPair.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
			}
			
			else if (this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank()) {
				copyPair.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
				copyPair.add(this.getCards().get(eCardNo.FifthCard.getCardNo()));
			}
			
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 0; i < this.getCards().size(); i ++) {
				for (int j = 0; j < copyPair.size(); j ++) {
					if (this.getCards().get(i).getRank().getRank() == copyPair.get(j).getRank().getRank()) {
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
			
			//Two Pair test
			
			System.out.println("Am pair");
			this.setPair(true);
			if (this.isTwoPair() == false) {
			ScoreHand(eHandStrength.Pair, hiCardPair, 0, this.getKicker());
			}
		}*/
		//Pair and 2 Pair test
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
			
			if (pairHand.size() == 4) {
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
				System.out.println("Am 2 pair");
				this.setTwoPair(true);
				ScoreHand(eHandStrength.TwoPair, highcard, lowcard, this.getKicker());
		
			}
			
			else if (pairHand.size() == 2) {
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
		
		else {
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < this.getCards().size(); i++ ) {
				this.addKicker(this.getCards().get(i));
			}
			System.out.println("Am high card");
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
				System.out.println("High straight");
				this.setStraight(true);
				// Looks for Ace, 2, 3, 4, 5
			} else if (this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& this.getCards().get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& this.getCards().get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.FIVE) {
				System.out.println("Low straight");
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
			System.out.println("Straight worked");
			this.setStraight(true);
		} else {
			this.setStraight(false);
		}

		// Evaluate Royal Flush
		if (this.isStraight()
				&& Flush == true
				&& this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			System.out.println("Is royal flush");
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, this.getKicker());
			this.setRoyalFlush(true);
			this.setStraightFlush(false);
		}

		// Straight Flush
		else if (this.isStraight() && Flush == true) {
			System.out.println("Is straight flush");
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
			System.out.println("Came through straight");
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			ScoreHand(eHandStrength.Straight, this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}
		
		// Flush
		else if (Flush == true & this.isRoyalFlush() == false & this.isStraightFlush() == false) {
			System.out.println("Is normal flush");
			int hiCardFlush = 0;
			ArrayList<Card> kicker = new ArrayList<Card>();
			this.setKicker(kicker);
			for (int i = 1; i < this.getCards().size(); i++) {
				this.addKicker(this.getCards().get(i));
			}
			ScoreHand(eHandStrength.Flush, this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, this.getKicker());
		}
		
		
		// Four of a Kind
		

		// Full House
		if (this.isThreeofKind() == true) {
			System.out.println("I was recognized as a full house");
			ArrayList<Card> copyHand;
			copyHand = this.getCards();
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(0);
				}
				System.out.println(copyHand.get(0).getRank().getRank());
				System.out.println(copyHand.get(1).getRank().getRank());
			}
			
			
			else if (copyHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == copyHand.get(eCardNo.FifthCard.getCardNo()).getRank()) {
				for (int i = 0; i < 3; i++) {
					copyHand.remove(2);
				}
				System.out.println(copyHand.get(0).getRank().getRank());
				System.out.println(copyHand.get(1).getRank().getRank());
			}
			
			if (copyHand.get(eCardNo.FirstCard.getCardNo()).getRank() == copyHand.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				ArrayList<Card> kicker = new ArrayList<Card>();
				this.setKicker(kicker);
				this.setFullHouse(true);
				System.out.println("Is full house");
				ScoreHand(eHandStrength.FullHouse, eHandStrength.ThreeOfAKind.getHandStrength(), eHandStrength.Pair.getHandStrength(), this.getKicker());
		}

		
		
		
		
		// Three of a Kind
		
		
		// Two Pair
		if (Pair == true) {
			ArrayList<Card> pairHand = new ArrayList<Card>();
			
			if (this.getCards().get(eCardNo.FirstCard.getCardNo()).getRank() == this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank()) {
				pairHand.add(this.getCards().get(eCardNo.FirstCard.getCardNo()));
				pairHand.add(this.getCards().get(eCardNo.SecondCard.getCardNo()));	
			}
			
			else if (this.getCards().get(eCardNo.SecondCard.getCardNo()).getRank() == this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				{
					pairHand.add(this.getCards().get(eCardNo.SecondCard.getCardNo()));
					pairHand.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
				}
			}
			
			else if (this.getCards().get(eCardNo.ThirdCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank()) {
				pairHand.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
				pairHand.add(this.getCards().get(eCardNo.ThirdCard.getCardNo()));
				}
			
			else if (this.getCards().get(eCardNo.FourthCard.getCardNo()).getRank() == this.getCards().get(eCardNo.FifthCard.getCardNo()).getRank()) {
				
					pairHand.add(this.getCards().get(eCardNo.FourthCard.getCardNo()));
					pairHand.add(this.getCards().get(eCardNo.FifthCard.getCardNo()));
			}
			
			if (pairHand.size() == 4) {
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
				System.out.println("Am 2 pair");
				ScoreHand(eHandStrength.TwoPair, highcard, lowcard, this.getKicker());
		
			}
			else
				this.setTwoPair(false);
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

	public boolean isTwoPair() {
		return TwoPair;
	}
	public void setTwoPair(boolean twoPair) {
		TwoPair = twoPair;
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
