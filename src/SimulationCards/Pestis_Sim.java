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

public class Pestis_Sim extends Simtemplate {
	//"id":201,"name":"Pestis","description":"Enchanted creature has +1 Attack for each Rat on that side. When enchanted creature destroys another unit, summon a <Mangy Rat> on a random tile the same side."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	//TODO test buff a rat  with it
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff =0;
		for(Minion m : b.getPlayerFieldList(target.position.color))
		{
			if(m.getSubTypes().contains(SubType.Rat)) buff++;
		}
		target.buffMinionWithoutMessage(buff, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Pestis", playedCard.card.cardDescription, playedCard, b);
        return;
    }

	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.owner.position.color && summonedMinion.getSubTypes().contains(SubType.Rat))
		{
			triggerEffectMinion.owner.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {

		if(attacker == triggerEffectMinion.owner && triggerEffectMinion.owner != diedMinion)
		{
			//summon mangy rat somewhree
			
			Card c = CardDB.getInstance().cardId2Card.get(165);
			Minion ill = new Minion(c, -1, attacker.position.color);
			
			ArrayList<UPosition> poses = b.getFreePositions(attacker.position.color);
			if(poses.size()==0) return;
			
			int randomint = b.getRandomNumber(0, poses.size()-1);
			b.summonUnitOnPosition(poses.get(randomint), ill);
			
			return;
		}
		
		if(diedMinion.position.color == triggerEffectMinion.owner.position.color && diedMinion.getSubTypes().contains(SubType.Rat))
		{
			triggerEffectMinion.owner.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
	
	 public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.owner.position.color == m.position.color && subt == SubType.Rat)
		 {
			 triggerEffectMinion.owner.buffMinion(1, 0, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.owner.position.color == m.position.color && subt == SubType.Rat)
		 {
			 triggerEffectMinion.owner.buffMinion(-1, 0, 0, b);
		 }
		 return;
	 }
	
}
