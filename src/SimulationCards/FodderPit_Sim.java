package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class FodderPit_Sim extends Simtemplate {
	//"id":340,"name":"Fodder Pit","description":"All units with [Pillage] have +2 Attack."
	//linger 5

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 5;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
		
		if(!isNew) return;
		
		//buff pillage minions
		
		for(Minion m : b.getAllMinionOfField())
		{
			if(m.card.hasPillage) m.buffMinion(2, 0, 0, b);
		}
		
	    return;
	 }

	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
		 	if(summonedMinion.card.hasPillage) summonedMinion.buffMinion(2, 0, 0, b);
	        return;
	    }
	 
	 public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
	 {
		 for(Minion mnn : b.getAllMinionOfField())
		 {
			if(mnn.card.hasPillage) mnn.buffMinion(-2, 0, 0, b);
		 }
	    return;
	 }
}
