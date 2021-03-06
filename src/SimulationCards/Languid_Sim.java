package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Languid_Sim extends Simtemplate 
{

	//"id":181,"name":"Languid","description":"Enchanted unit's Attack is decreased by 2. When Languid comes into play, draw 1 scroll."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(-2, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Languid", playedCard.card.cardDescription, playedCard, b);
		
		b.drawCards(playedCard.position.color, 1);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(2, 0, 0, b);
        return;
    }
	
}
