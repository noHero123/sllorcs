package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class PiercingProjectile_Sim extends Simtemplate 
{

	//"id":262,"name":"Piercing Projectile","description":"Enchanted ranged creature gets [Piercing] and +1 Attack."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_ranged_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(1, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Piercing Projectile", playedCard.card.cardDescription, playedCard, b);
        return;
    }

	
	public boolean hasPiercing(Board b ,Minion m)
    {
    	return true;
    }
}
