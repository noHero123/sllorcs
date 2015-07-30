package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Fleetness_Sim extends Simtemplate 
{

	//"id":234,"name":"Fleetness","description":"Enchanted unit's [base Countdown] is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		if(target.maxAc>=1)
			{
			target.maxAc--;
			}
			
		target.addCardAsEnchantment("ENCHANTMENT", "Fleetness", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	if(m.maxAc>=0)
		{
		m.maxAc++;
		}
        return;
    }
	
}
