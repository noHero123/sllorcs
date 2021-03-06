package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ResonantHelm_Sim extends Simtemplate 
{

	//"id":124,"name":"Resonant Helm","description":"" magic ressi +1, resonance: heal 2 (effect on play a spell
	// EDIT for ressonance.. the unit counts.. not the owner of the spell!!!
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Resonant Helm", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public int getMagicResistance(Board b ,Minion triggerEffectMinion, Minion minion)
    {
    	return 1;
    }
	
	public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
    {
		if(spell.position.color != triggerEffectMinion.owner.position.color) return; // TESTED IT!!!
		
		triggerEffectMinion.owner.healMinion(2, b);
    	return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
	
}
