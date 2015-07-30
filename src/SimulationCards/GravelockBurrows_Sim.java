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

public class GravelockBurrows_Sim extends Simtemplate {
	//"id":339,"name":"Gravelock Burrows","description":"When a Gravelock you control is dealt damage, your other Gravelocks get +1 Attack until end of your turn."
	//linger 8

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 8;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
	    return;
	 }
	 
	 public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
	    {
		 
		 	if(damagedMinion.position.color == triggerEffectMinion.position.color && damagedMinion.getSubTypes().contains(SubType.Gravelock))
		 	{
		 		for(Minion m : b.getPlayerFieldList(triggerEffectMinion.position.color))
		 		{
		 			if( m != damagedMinion && m.getSubTypes().contains(SubType.Gravelock))
		 			{
		 				m.buffMinionWithoutMessage(1, 0, 0, b);
		 				m.addnewEnchantments("BUFF", "Gravelock Burrows", triggerEffectMinion.card.cardDescription, triggerEffectMinion.card, b, triggerEffectMinion.position.color);
		 			}
		 		}
		 	}
	        return;
	    }

	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	    {

		 	if(triggerEffectMinion.owner==null) return false;
	        return true;//buff is removed, so we return true
	    }
	 
		public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
	    {
		 	if(m.owner== null) return;
		 	m.owner.buffMinionWithoutMessage(-1, 0, 0, b);
	        return;
	    }
}
