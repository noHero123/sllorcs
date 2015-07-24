package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class EternalStatue_Sim extends Simtemplate
{
	//"id":60,"name":"Eternal Statue","description":"" (regen 1)

	
	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	{
		if(turnStartColor == triggerEffectMinion.position.color)
		{
			triggerEffectMinion.healMinion(1,b);
		}
	}
	
}
