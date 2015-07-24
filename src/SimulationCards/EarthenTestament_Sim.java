package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class EarthenTestament_Sim extends Simtemplate {
	//"id":291,"name":"Earthen Testament","description":"Enchanted units you control have their Countdown decreased by 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.hasEnchantment())
			{
				m.buffMinion(0, 0, -2, b);
				
			}
		}
        return;
    }
	
	
}
