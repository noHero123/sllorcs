package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class DivineMark_Sim extends Simtemplate {
	//"id":158,"name":"Divine Mark","description":"When enchanted creature is destroyed, it is returned to owner's hand. If it was destroyed by damage from another unit, that unit is returned to its owner's hand."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Divine Mark", "", playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		{
			diedMinion.addToHandAfterDead=true;
			if(attacker.cardType == Kind.CREATURE || attacker.cardType == Kind.STRUCTURE)
			{
				if(attacker.Hp>=1)
				{
					attacker.Hp = 0;
					b.removeMinionToHand(attacker);
				}
				else
				{
					attacker.addToHandAfterDead=true;
				}
				
			}
		}
			
        return;
    }
	
	
}
