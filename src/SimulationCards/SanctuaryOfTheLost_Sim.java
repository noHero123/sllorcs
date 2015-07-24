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

public class SanctuaryOfTheLost_Sim extends Simtemplate {
	//"id":342,"name":"Sanctuary of the Lost","description":"Undead creatures have [Ward] and [Magic resistance] 1."
	//linger 5

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 5;
	 }
	 
	 public int getMagicResistance(Board b ,Minion triggerEffectMinion, Minion minion)
	 {
		 //doenst has to be checked :D (it is checked before triggered
		 if(minion.getSubTypes().contains(SubType.Undead) && minion.cardType == Kind.CREATURE)
			{
			 	return 1;
			}
		 return 0;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		b.addRule(playedCard);
	    return;
	 }

}
