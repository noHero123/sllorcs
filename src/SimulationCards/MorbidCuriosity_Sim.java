package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class MorbidCuriosity_Sim extends Simtemplate 
{

	//"id":265,"name":"Morbid Curiosity","description":"When enchanted creature destroys another creature, enchanted unit's owner draws 1 scroll. When enchanted unit is destroyed, draw 1 scroll."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Morbid Curiosity", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion == triggerEffectMinion.owner)
		{
			b.drawCards(triggerEffectMinion.owner.position.color, 1);
			return;
		}
		
		if(diedMinion != triggerEffectMinion.owner && attacker == triggerEffectMinion.owner && diedMinion.cardType == Kind.CREATURE)
		{
			b.drawCards(triggerEffectMinion.owner.position.color, 1);
			return;
		}
			
        return;
    }
	
}
