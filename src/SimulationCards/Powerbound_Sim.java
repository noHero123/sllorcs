package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class Powerbound_Sim extends Simtemplate 
{

	//"id":95,"name":"Powerbound","description":"When enchanted unit you control is destroyed, increase Order by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;//tested
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Powerbound", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(diedMinion.position.color == triggerEffectMinion.owner.position.color)
		{
			b.changeMaxRessource(ResourceName.ORDER, diedMinion.position.color, 1);
		}
        return;
    }
	
}
