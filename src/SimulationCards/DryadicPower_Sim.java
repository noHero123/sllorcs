package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class DryadicPower_Sim extends Simtemplate 
{

	//"id":45,"name":"Dryadic Power","description":"Enchanted creature gets +1 Attack and +3 Health, and its [Move] is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.moveChanges-=1;
		target.buffMinionWithoutMessage(1, 3,0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Dryadic Power", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-1, -3, 0, b);
	 	m.owner.moveChanges+=1;
        return;
    }
	
}
