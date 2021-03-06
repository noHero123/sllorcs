package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Machinated_Sim extends Simtemplate 
{

	//"id":235,"name":"Machinated","description":"Enchanted creature gets +5 Attack, and its [base Countdown] is doubled."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 5, 0, b);//status update is done in add card as enchantment
		playedCard.turnCounter = target.maxAc;
		target.maxAc*=2;
		target.addCardAsEnchantment("ENCHANTMENT", "Machinated", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(0, -5, 0, b);
	 	m.owner.maxAc -= m.turnCounter;
	 	m.turnCounter=0;
        return;
    }
	
}
