package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class Bloodboil_Sim extends Simtemplate 
{

	//id":104,"name":"Bloodboil","description":"When enchanted unit takes damage, it gets +2 Attack." + replenish
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		//replenish
		int x = playedCard.card.costGrowth + playedCard.card.costOrder + playedCard.card.costEnergy + playedCard.card.costDecay;
		b.changeCurrentRessource(ResourceName.WILD, playedCard.position.color, x);
		
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Bloodboil", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
    {
		if(dmg<=0 || triggerEffectMinion.owner != damagedMinion)
		{
			return;
		}
		
		damagedMinion.buffMinion(2, 0, 0, b);
		
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
}
