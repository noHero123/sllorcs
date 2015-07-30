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

public class Animovore_Sim extends Simtemplate {
	//"id":171,"name":"Animovore","description":"Enchant unit you control. When a unit adjacent to enchanted unit is destroyed, enchanted unit is dealt 1 [pure damage] and you draw 1 scroll."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Animovore", "", playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.position.isNeightbour(triggerEffectMinion.owner.position))
		{
			b.doDmg(triggerEffectMinion.owner, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
			b.drawCards(triggerEffectMinion.owner.position.color, 1);
		}
			
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
        return;
    }
	
	
}
