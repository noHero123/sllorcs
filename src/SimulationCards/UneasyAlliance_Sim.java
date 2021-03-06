package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class UneasyAlliance_Sim extends Simtemplate 
{

	//"id":251,"name":"Uneasy Alliance","description":"Enchanted creature gets +2 Attack. When it is destroyed, all other creatures with Uneasy Alliance are destroyed."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
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
			if(e==diedMinion) continue;
			if(e.typeId == 251)
			{
				destroy=true;
			}
		}
		
		b.destroyMinion(triggerEffectMinion.owner, triggerEffectMinion);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return;
    }
	
	
}
