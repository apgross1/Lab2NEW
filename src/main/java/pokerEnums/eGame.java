package pokerEnums;

import java.util.ArrayList;

public enum eGame {

	FiveStud(1),
	FiveStudOneJoker(2),
	FiveStudTwoJoker(3),
	TexasHoldEm(4),
	Omaha(5),
	DeucesWild(6),
	AcesAndEights(7),
	SevenDraw(8);
	
	private int gameNbr;

	private eGame(final int gameNbr){
		this.gameNbr = gameNbr;
	}
	
	/**
	 * Getter that returns value which indicates the game being played
	 * @return
	 */
	public int getGame(){
		return gameNbr;
	}
}
