package pokerBase;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;

public class EvalTest extends Hand {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void straightTest() {
		Card card1 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card2 = new Card(eSuit.DIAMONDS, eRank.THREE, false);
		Card card3 = new Card(eSuit.CLUBS, eRank.FOUR, false);
		Card card4 = new Card(eSuit.DIAMONDS, eRank.FIVE, false);
		Card card5 = new Card(eSuit.SPADES, eRank.SIX, false);
		
		Hand hand1 = new Hand();
		hand1.AddCardToHand(card1);
		hand1.AddCardToHand(card2);
		hand1.AddCardToHand(card3);
		hand1.AddCardToHand(card4);
		hand1.AddCardToHand(card5);
		
		Card card6 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card7 = new Card(eSuit.HEARTS, eRank.THREE, false);
		Card card8 = new Card(eSuit.HEARTS, eRank.FOUR, false);
		Card card9 = new Card(eSuit.HEARTS, eRank.FIVE, false);
		Card card10 = new Card(eSuit.HEARTS, eRank.SIX, false);
		
		Hand hand2 = new Hand();
		hand2.AddCardToHand(card6);
		hand2.AddCardToHand(card7);
		hand2.AddCardToHand(card8);
		hand2.AddCardToHand(card9);
		hand2.AddCardToHand(card10);
		
		
		hand1.EvalHand();
		hand2.EvalHand();
		//Assertion test for evalHand
		assertTrue(hand1.getHandStrength() == eHandStrength.Straight.getHandStrength());
		
		//Assertion test for comparing straight and straight flush
		assertTrue(Hand.HandRank.compare(hand1, hand2) > 0);
	}
	
	@Test
	public void flushTest() {
		Card card1 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card2 = new Card(eSuit.HEARTS, eRank.EIGHT, false);
		Card card3 = new Card(eSuit.HEARTS, eRank.FOUR, false);
		Card card4 = new Card(eSuit.HEARTS, eRank.SEVEN, false);
		Card card5 = new Card(eSuit.HEARTS, eRank.TEN, false);
		
		Hand hand1 = new Hand();
		hand1.AddCardToHand(card1);
		hand1.AddCardToHand(card2);
		hand1.AddCardToHand(card3);
		hand1.AddCardToHand(card4);
		hand1.AddCardToHand(card5);
		
		Card card6 = new Card(eSuit.HEARTS, eRank.TEN, false);
		Card card7 = new Card(eSuit.HEARTS, eRank.JACK, false);
		Card card8 = new Card(eSuit.HEARTS, eRank.QUEEN, false);
		Card card9 = new Card(eSuit.HEARTS, eRank.KING, false);
		Card card10 = new Card(eSuit.HEARTS, eRank.ACE, false);
		Hand hand2 = new Hand();
		hand2.AddCardToHand(card6);
		hand2.AddCardToHand(card7);
		hand2.AddCardToHand(card8);
		hand2.AddCardToHand(card9);
		hand2.AddCardToHand(card10);

		hand1.EvalHand();
		hand2.EvalHand();
		
		//Assertion test for evalHand
		assertTrue(hand1.getHandStrength() == eHandStrength.Flush.getHandStrength());
		assertTrue(hand2.getHandStrength() == eHandStrength.RoyalFlush.getHandStrength());
		
		//Assertion test for comparing flush and royal flush
		assertTrue(Hand.HandRank.compare(hand1, hand2) > 0);
	}
	
	@Test
	public void kindTest() {
		Card card1 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card2 = new Card(eSuit.DIAMONDS, eRank.TWO, false);
		Card card3 = new Card(eSuit.SPADES, eRank.TWO, false);
		Card card4 = new Card(eSuit.CLUBS, eRank.FIVE, false);
		Card card5 = new Card(eSuit.HEARTS, eRank.TEN, false);
		
		Hand hand1 = new Hand();
		hand1.AddCardToHand(card1);
		hand1.AddCardToHand(card2);
		hand1.AddCardToHand(card3);
		hand1.AddCardToHand(card4);
		hand1.AddCardToHand(card5);
		
		Card card6 = new Card(eSuit.SPADES, eRank.TWO, false);
		Card card7 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card8 = new Card(eSuit.DIAMONDS, eRank.TWO, false);
		Card card9 = new Card(eSuit.HEARTS, eRank.KING, false);
		Card card10 = new Card(eSuit.CLUBS, eRank.TEN, false);
		Hand hand2 = new Hand();
		hand2.AddCardToHand(card6);
		hand2.AddCardToHand(card7);
		hand2.AddCardToHand(card8);
		hand2.AddCardToHand(card9);
		hand2.AddCardToHand(card10);

		hand1.EvalHand();
		hand2.EvalHand();
		//Assertion test for evalHand
		assertTrue(hand1.getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(hand2.getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		
		//Assertion test for comparing two three of a kinds with the same value; tests the kickers
		assertTrue(Hand.HandRank.compare(hand1, hand2) > 0);
	}
	
	@Test
	public void fullHouseTest() {
		Card card1 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card2 = new Card(eSuit.DIAMONDS, eRank.TWO, false);
		Card card3 = new Card(eSuit.SPADES, eRank.TWO, false);
		Card card4 = new Card(eSuit.CLUBS, eRank.THREE, false);
		Card card5 = new Card(eSuit.HEARTS, eRank.THREE, false);
		
		Hand hand1 = new Hand();
		hand1.AddCardToHand(card1);
		hand1.AddCardToHand(card2);
		hand1.AddCardToHand(card3);
		hand1.AddCardToHand(card4);
		hand1.AddCardToHand(card5);
		
		Card card6 = new Card(eSuit.SPADES, eRank.JACK, false);
		Card card7 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card8 = new Card(eSuit.DIAMONDS, eRank.FIVE, false);
		Card card9 = new Card(eSuit.HEARTS, eRank.KING, false);
		Card card10 = new Card(eSuit.CLUBS, eRank.TEN, false);
		Hand hand2 = new Hand();
		hand2.AddCardToHand(card6);
		hand2.AddCardToHand(card7);
		hand2.AddCardToHand(card8);
		hand2.AddCardToHand(card9);
		hand2.AddCardToHand(card10);

		hand1.EvalHand();
		hand2.EvalHand();
		System.out.println(hand1.getHandStrength());
		System.out.println(hand2.getHandStrength());
		//Assertion test for evalHand
		assertTrue(hand1.getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(hand2.getHandStrength() == eHandStrength.HighCard.getHandStrength());
		
		//Assertion test for comparing full house and high card
		assertTrue(Hand.HandRank.compare(hand1, hand2) < 0);
	}
	
	@Test
	public void pairTest() {
		
		Card card1 = new Card(eSuit.HEARTS, eRank.TWO, false);
		Card card2 = new Card(eSuit.DIAMONDS, eRank.TWO, false);
		Card card3 = new Card(eSuit.SPADES, eRank.FIVE, false);
		Card card4 = new Card(eSuit.CLUBS, eRank.ACE, false);
		Card card5 = new Card(eSuit.HEARTS, eRank.THREE, false);
		
		
		Hand hand1 = new Hand();
		hand1.AddCardToHand(card1);
		hand1.AddCardToHand(card2);
		hand1.AddCardToHand(card3);
		hand1.AddCardToHand(card4);
		hand1.AddCardToHand(card5);
		
		Card card6 = new Card(eSuit.SPADES, eRank.JACK, false);
		Card card7 = new Card(eSuit.HEARTS, eRank.JACK, false);
		Card card8 = new Card(eSuit.DIAMONDS, eRank.FIVE, false);
		Card card9 = new Card(eSuit.HEARTS, eRank.KING, false);
		Card card10 = new Card(eSuit.CLUBS, eRank.KING, false);
		Hand hand2 = new Hand();
		hand2.AddCardToHand(card6);
		hand2.AddCardToHand(card7);
		hand2.AddCardToHand(card8);
		hand2.AddCardToHand(card9);
		hand2.AddCardToHand(card10);

		hand1.EvalHand();
		hand2.EvalHand();
		System.out.println(hand2.getHandStrength());
		System.out.println(hand1.getHandStrength());
		
		
		//Assertion test for evalHand
		assertTrue(hand1.getHandStrength() == eHandStrength.Pair.getHandStrength());
		assertTrue(hand2.getHandStrength() == eHandStrength.TwoPair.getHandStrength());
		
		
		
		//Assertion test for comparing two pair and pair
		assertTrue(Hand.HandRank.compare(hand1, hand2) > 0);
	}
}
