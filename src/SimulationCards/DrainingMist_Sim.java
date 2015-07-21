package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class DrainingMist_Sim extends Simtemplate {
	//"id":192,"name":"Draining Mist","description":"All opponent units with Countdown 1 or less have their Countdown increased by 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		Color oppcol = Board.getOpposingColor(b.activePlayerColor);
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(oppcol));
		for(Minion m : all)
		{
			if(m.getAc()<=1 && m.maxAc>=0)
			{
				m.buffMinion(0, 0, 2, b);
			}
		}
		

		
        return;
    }
	
}
