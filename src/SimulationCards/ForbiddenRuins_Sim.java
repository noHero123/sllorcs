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

public class ForbiddenRuins_Sim extends Simtemplate {
	//"id":344,"name":"Forbidden Ruins","description":"All non-combat damage against idols is increased by 1."
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
		b.addRule(playedCard);
	    return;
	 }
	 
	 public int getIdolDamageBonus(Board b ,Minion m, AttackType attackType, DamageType damageType)
	 {
		 if(damageType == DamageType.COMBAT) return 0;
		 
		 return 1;
	 }

}
