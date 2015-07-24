package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Rallying_Sim extends Simtemplate {
	//"id":21,"name":"Rallying","description":"Units you control have their Countdown decreased by 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(b.activePlayerColor));
		for(Minion m : all)
		{
			m.buffMinion(0, 0, -2, b);
		}
		

		
        return;
    }
	
}
