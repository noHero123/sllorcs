package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class Arthritis_Sim extends Simtemplate {
	//"id":176,"name":"Arthritis","description":"Each time enchanted unit deals damage, it is dealt [magic damage] equal to its [base Countdown]."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Arthritis", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone)
    {
		if(attacker != triggerEffectMinion.owner) return;
		int dmg = attacker.maxAc;
		b.doDmg(attacker, triggerEffectMinion, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
	
	
}