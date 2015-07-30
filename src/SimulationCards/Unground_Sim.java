package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Unground_Sim extends Simtemplate 
{

	//"id":310,"name":"Unground","description":"Any time an idol takes damage, enchanted unit is dealt [magic damage] equal to half that amount (rounded up)."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Unground", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
    {
		if(dmg <=0 || !damagedMinion.isIdol) return;

		int damage = dmg/2;
		if (dmg % 2 == 1) //dmg is uneven
		{ 
			damage=(dmg+1)/2;
		}
		b.doDmg(triggerEffectMinion.owner, triggerEffectMinion, damage, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
}
