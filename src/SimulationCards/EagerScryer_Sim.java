package SimulationCards;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class EagerScryer_Sim extends Simtemplate {
	//"id":255,"name":"Eager Scryer","description":"If Eager Scryer's Countdown is 2 or more at the end of your turn, draw 1 scroll."
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		if(triggerEffectMinion.position.color != turnEndColor) return false;
		if(triggerEffectMinion.getAc()>=2)
		{
			b.drawCards(turnEndColor, 1);
		}
		
        return false;
    }

	

}
