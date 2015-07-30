package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class VerdantVeil_Sim extends Simtemplate 
{

	//"id":294,"name":"Verdant Veil","description":"Enchanted creature gets +3 Health. As long as its Health is no less than its [base Health], it has [Ward]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 3, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Verdant Veil", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public boolean hasWard(Board b ,Minion m)
    {
		if(m.owner== null) return false;
		if(m.owner.Hp>=m.owner.card.hp) return true;
    	return false;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(0, -3, 0, b);
        return;
    }
	
}
