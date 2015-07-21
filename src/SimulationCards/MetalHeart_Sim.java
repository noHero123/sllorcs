package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
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
			if(m.subtypes.contains(subType.Automaton))
			{
				buffs++;
			}
		}
		if(buffs>=1) target.buffMinionWithoutMessage(buffs, 0, 0, b);
		boolean triggerOtherMetalHeart = false;
		if(!target.subtypes.contains(subType.Automaton))
		{
			triggerOtherMetalHeart=true;
		}
		target.subtypes.add(subType.Automaton);
		target.addCardAsEnchantment("ENCHANTMENT", "Bear Paw", playedCard.card.cardDescription, playedCard, b);
		
		if(triggerOtherMetalHeart)
		{
			for(Minion mnn: b.getPlayerFieldList(target.position.color))
			{
				if(mnn == target) continue;
				
				for(Minion e : mnn.getAttachedCards())
				{
					if(e.typeId == 151) e.card.cardSim.onMinionIsSummoned(b, e, target);
				}
			}
		}
        return;
    }
	
	// TODO we do a special onDeathrattle only for enchantments in addMinionToGrave!
	public  void onDeathrattle(Board b, Minion m)
    {
		//remove attack of others metelhearted minions
		if(m.owner.card.subtypes.contains(subType.Automaton)) 
		{
			return; // dont have to debuff minions
		}
		
		for(Minion mnn: b.getPlayerFieldList(m.owner.position.color))
		{
			if(mnn == m.owner) continue;
			
			for(Minion e : mnn.getAttachedCards())
			{
				if(e.typeId == 151) e.card.cardSim.onMinionDiedTrigger(b, e, m.owner, e, AttackType.UNDEFINED, DamageType.PHYSICAL);
			}
		}
		
		//delete subtype

        for ( int i = 0;  i < m.subtypes.size(); i++)
        {
        	subType tempName = m.subtypes.get(i);
            if(tempName.equals(subType.Automaton))
            {
            	 m.subtypes.remove(i);
            	 //break;
            }
        }
		
        return;
    }
	
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.subtypes.contains(subType.Automaton))
		{
			triggerEffectMinion.owner.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.subtypes.contains(subType.Automaton))
		{
			triggerEffectMinion.owner.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
}
