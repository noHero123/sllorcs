package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class StifledAdvance_Sim extends Simtemplate 
{

	//"id":263,"name":"Stifled Advance","description":"Enchanted unit's [base Countdown] is increased by 2. When Stifled Advance comes into play, enchanted unit's Countdown is increased by 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.maxAc+=2;
		target.buffMinionWithoutMessage(0, 0, 2, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Stifled Advance", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.maxAc-=2;
        return;
    }
	
}
