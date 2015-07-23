package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class UneasyAlliance_Sim extends Simtemplate 
{

	//"id":251,"name":"Uneasy Alliance","description":"Enchanted creature gets +2 Attack. When it is destroyed, all other creatures with Uneasy Alliance are destroyed."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(2, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Uneasy Alliance", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		boolean destroy =false;
		for(Minion e : diedMinion.getAttachedCards())
		{
			if(e.typeId == 251)
			{
				destroy=true;
			}
		}
		
		b.destroyMinion(triggerEffectMinion.owner, triggerEffectMinion);
        return;
    }
	
}
