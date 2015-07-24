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

public class AncestralPact_Sim extends Simtemplate {
	//"id":261,"name":"Ancestral Pact","description":"When a unit you control is destroyed during your opponent's turn, draw 1 scroll, and Ancestral Pact counts down by 1."
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
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.position.color != diedMinion.position.color || triggerEffectMinion.position.color == b.activePlayerColor ) return;
		 
		 b.drawCards(triggerEffectMinion.position.color, 1);
		 b.ruleCountDown(triggerEffectMinion, 1);
			 
	     return;
	 }

}
