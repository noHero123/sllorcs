package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class BrainLice_Sim extends Simtemplate
{
	//"id":183,"name":"Brain Lice","description":"Enchanted creature becomes [poisoned]. When it is destroyed, draw 1 scroll."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Brain Lice", playedCard.card.cardDescription, playedCard, b);
		target.addnewPoison(b, playedCard.position.color);
        return;
    }
	
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion == triggerEffectMinion.owner)
		{
			b.drawCards(triggerEffectMinion.position.color, 1);
		}
			
        return;
    }
	
	
}
