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
import BattleStuff.Board.SummonItem;

public class AescalonSpires_Sim extends Simtemplate {
	//"id":541,"name":"Aescalon Spires","description":"When a unit is destroyed, it is resummoned on the same tile, and Aescalon Spires counts down by 1."
	//linger 3

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 3;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
	    return;
	 }
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	    {
			//unbuff wolf if a wolf dies
			if(!diedMinion.isIdol)
			{
				//summon illthorn
				Minion ill = new Minion(diedMinion.card, triggerEffectMinion.cardID, diedMinion.position.color);
				boolean issummoned = b.addItemToSummonList(b.new SummonItem(ill, diedMinion.position, 0));
				
				if(issummoned)//minion will be summoned!
				{
					//make dying minion to token. so it is not added to grave! :D
					triggerEffectMinion.cardID = -1;
				}
				b.ruleCountDown(triggerEffectMinion, 1);
				//b.summonUnitOnPosition(new Position(diedMinion.position), ill);
			}
				
	        return;
	    }

}
