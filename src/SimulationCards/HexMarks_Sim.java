package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class HexMarks_Sim extends Simtemplate {
	//"id":202,"name":"Hex Marks","description":"" 3 additional dmg when idol gets dmg
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Hex Marks", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion.owner || !damagedMinion.isIdol) return;
		if(dmgdone<=0) return;
		int dmg = 3;
		b.doDmg(damagedMinion, triggerEffectMinion, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
	
	
}
