package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class BountifulTimes_Sim extends Simtemplate {
	//"id":293,"name":"Bountiful Times","description":"Summon a <Bunny> on target tile. Other Bunnies have their Countdown decreased by 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_free;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.typeId == 129)
			{
				m.buffMinion(0, 0, -2, b);
			}
		}
		
		Card c = CardDB.getInstance().cardId2Card.get(129);
		Minion ill = new Minion(c, -1, playedCard.position.color);
		b.summonUnitOnPosition(targets.get(0), ill);
        
        return;
    }


}
