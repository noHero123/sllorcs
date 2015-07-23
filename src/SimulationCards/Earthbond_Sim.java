package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Earthbond_Sim extends Simtemplate 
{

	//"id":303,"name":"Earthbond","description":"Enchanted unit gets +2 Health." magic ressi
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	
	public int getMagicResistance(Board b ,Minion m)
    {
    	return 2;
    }
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 2, 0, b);//status update is done in add card as enchantment
		target.magicRessi+= this.getMagicResistance(b, playedCard);
		target.addCardAsEnchantment("ENCHANTMENT", "Bear Paw", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}
