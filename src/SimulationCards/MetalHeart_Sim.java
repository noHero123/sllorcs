package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class MetalHeart_Sim extends Simtemplate {
	//"id":151,"name":"Metal Heart","description":"Enchanted creature counts as an Automaton, and its Attack is increased by the number of other Automatons you control."
	//wording is wrong... should be, and its Attack is increased by the number of other Automatons its owner controls."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	//TODO are the other subtypes are deleted?
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));

		int buffs = 0;
		for(Minion m : b.getPlayerFieldList(target.position.color))
		{
			if(target.position.isEqual(m.position)) continue;
			
			if(m.getSubTypes().contains(SubType.Automaton))
			{
				buffs++;
			}
		}
		if(buffs>=1) target.buffMinionWithoutMessage(buffs, 0, 0, b);
		
		
		
		target.addSubtype(SubType.Automaton,b);
		target.addCardAsEnchantment("ENCHANTMENT", "Metal Heart", playedCard.card.cardDescription, playedCard, b);
	
        return;
    }
	
	// TODO we do a special onDeathrattle only for enchantments in addMinionToGrave!
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		//remove attack of others metelhearted minions
		m.removeSubtype(SubType.Automaton,b);
		
        return;
    }
	
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.getSubTypes().contains(SubType.Automaton))
		{
			triggerEffectMinion.owner.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.getSubTypes().contains(SubType.Automaton))
		{
			triggerEffectMinion.owner.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
	 public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 
		 if(triggerEffectMinion.owner == m) return;
		 
		 if(triggerEffectMinion.owner.position.color == m.position.color  && subt == SubType.Automaton)
		 {
			 triggerEffectMinion.owner.buffMinion(1, 0, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.owner == m) return;
		 
		 if(triggerEffectMinion.owner.position.color == m.position.color && subt == SubType.Automaton)
		 {
			 triggerEffectMinion.owner.buffMinion(-1, 0, 0, b);
		 }
		 return;
	 }
	
	
	
}
