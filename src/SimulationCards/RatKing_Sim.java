package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class RatKing_Sim extends Simtemplate {
	//"id":215,"name":"Rat King","description":"Target tile and adjacent tiles. Summon three <Beast Rats> within the spell's area.
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_all;//TODO test?
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Position center = targets.get(0);
		ArrayList<Position> temp = center.getNeightbours(); 
		temp.add(center);
		
		
		for(int i=0; i<3 ; i++)//summon 3 beastrats on target + neighbours
		{
			ArrayList<Position> freenbrs  = b.getFreePositionsFromPosition(temp); 
			if(freenbrs.size()==0) return;
		
			int rndm = b.getRandomNumber(0, freenbrs.size()-1);
			Card c = CardDB.getInstance().cardId2Card.get(189);
			Minion ill = new Minion(c, -1, playedCard.position.color);
			b.summonUnitOnPosition(freenbrs.get(rndm), ill);
		}
		
        return;
    }
	
}
