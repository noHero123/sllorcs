package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class EmberBonds_Sim extends Simtemplate
{
	
	//"id":80,"name":"Ember Bonds","description":"Enchanted unit is dealt 3 [magic damage] before attacking."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Ember Bonds", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onUnitIsGoingToAttack(Board b, Minion triggerEffectMinion, Minion attacker )
    {
		b.doDmg(attacker, triggerEffectMinion, 3, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
}
