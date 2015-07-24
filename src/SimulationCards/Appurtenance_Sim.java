package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Appurtenance_Sim extends Simtemplate {
	//"id":216,"name":"Appurtenance","description":"Enchanted unit gets +1 Health for each Beast you control."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff =0;
		for(Minion m : b.getPlayerFieldList(target.position.color))
		{
			if(m.getSubTypes().contains(SubType.Beast)) buff++;
		}
		target.buffMinionWithoutMessage(0, buff, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Appurtenance", playedCard.card.cardDescription, playedCard, b);
        return;
    }

	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.owner.position.color && summonedMinion.getSubTypes().contains(SubType.Beast))
		{
			triggerEffectMinion.owner.buffMinion(0, 1, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.position.color == triggerEffectMinion.owner.position.color && diedMinion.getSubTypes().contains(SubType.Beast))
		{
			triggerEffectMinion.owner.buffMinion(0, -1, 0, b);
		}
        return;
    }
	
	 public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.owner.position.color == m.position.color && subt == SubType.Beast)
		 {
			 triggerEffectMinion.owner.buffMinion(0, 1, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.owner.position.color == m.position.color && subt == SubType.Beast)
		 {
			 triggerEffectMinion.owner.buffMinion(0, -1, 0, b);
		 }
		 return;
	 }
}
