package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UPosition;
import BattleStuff.Board.SummonItem;

public class NogNest_Sim extends Simtemplate {
	//id":378,"name":"Nog Nest","description":"When Nog Nest's Countdown becomes 0, Nog Nest is destroyed and 2 <Nogs> are summoned on adjacent tiles."
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
    	return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		ArrayList<UPosition> freepos = b.getFreePositionsFromPosition(m.position.getNeightbours());
		if(freepos.size()==0)
		{
			b.destroyMinion(m, m);
			return;
		}
		int random = b.getRandomNumber(0, freepos.size()-1);
		Card c = CardDB.getInstance().cardId2Card.get(371);
		Minion ill = new Minion(c, -1, m.position.color);
		b.summonUnitOnPosition(freepos.get(random), ill);
		
		freepos = b.getFreePositionsFromPosition(m.position.getNeightbours());
		if(freepos.size()==0)
		{
			b.destroyMinion(m, m);
			return;
		}
		random = b.getRandomNumber(0, freepos.size()-1);
		Minion ill2 = new Minion(c, -1, m.position.color);
		b.summonUnitOnPosition(freepos.get(random), ill2);
		
		b.destroyMinion(m, m);
    	return;
    }
	
	
	
}
