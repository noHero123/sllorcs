package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class CrownOfStrength_Sim extends Simtemplate {
	//"id":15,"name":"Crown of Strength","description":"Enchanted unit gets +1 Attack and +2 Health, and counts as a Knight."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	//TODO are the other subtypes are deleted?
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));

		target.buffMinionWithoutMessage(1, 2, 0, b);

		boolean triggerOtherMetalHeart = false;
		if(!target.subtypes.contains(subType.Automaton))
		{
			triggerOtherMetalHeart=true;
		}
		if(triggerOtherMetalHeart)
		{
			//TODO add lingereffects to the new knight minion!
		}
		target.subtypes.add(subType.Knight);
		target.addCardAsEnchantment("ENCHANTMENT", "Crown of Strength", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	// TODO we do a special onDeathrattle only for enchantments in addMinionToGrave!
	public  void onDeathrattle(Board b, Minion m)
    {
		//remove attack of others metelhearted minions
		if(m.owner.card.subtypes.contains(subType.Knight)) 
		{
			return; // dont have to debuff minions
		}
		
		//delete subtype

        for ( int i = 0;  i < m.subtypes.size(); i++)
        {
        	subType tempName = m.subtypes.get(i);
            if(tempName.equals(subType.Knight))
            {
            	 m.subtypes.remove(i);
            	 //break;
            }
        }
		
        return;
    }
	
}
