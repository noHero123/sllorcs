package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class SlitheringForm_Sim extends Simtemplate 
{

	//"id":380,"name":"Slithering Form","description":"Enchanted melee unit passes through enemy structures and is immune to damage dealt by structures."
	//passing through structures is done in minion ->gettargets
	
	//it only works with real attacks!
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_melees;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Slithering Form", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	
	public boolean isImuneToDmg(Board b , Minion triggerEffectMinion,Minion deffender, Minion attacker, AttackType attackType, DamageType damageType)
    {
		if(triggerEffectMinion.owner==deffender && attacker.cardType == Kind.STRUCTURE) return true;
    	return false;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
}
