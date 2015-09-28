package pokerBase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Deck_Test {

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

	/**
	 * Tests if the deck is shuffled and instantiated with 52 instance of Card
	 */
	@Test
	public void TestFullDeck() {
		Deck deck = new Deck();
		//Does the deck have 52 elements?
		assertTrue("Test fails", 52 == deck.getTotalCards());
		
		//Are the elements in Deck instances of Card?
		assertTrue("Test fails", deck.drawFromDeck() instanceof Card);
		
		//Is the deck shuffled?
		assertFalse("Deck is in order", deck.drawFromDeck().getRank().getRank() == (deck.drawFromDeck().getRank().getRank() + 1));
		
	}

}
