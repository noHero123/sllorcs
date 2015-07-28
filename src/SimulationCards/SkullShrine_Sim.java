package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class SkullShrine_Sim extends Simtemplate {
	//"id":341,"name":"Skull Shrine","description":"At the beginning of your turns, increase [current] Energy by 2."
	//linger 4

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 4;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
	    return;
	 }
	 
	    public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	    {
	    	if(triggerEffectMinion.position.color == turnStartColor)
	    	{
	    		b.changeCurrentRessource(ResourceName.ENERGY, triggerEffectMinion.position.color, 2);
	    	}
	        return;
	    }

}
