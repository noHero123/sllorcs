package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class PlateArmor_Sim extends Simtemplate 
{

	//"id":86,"name":"Plate Armor","description":"Enchanted unit gets +1 Health."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.armor+= this.getArmor(b, playedCard);
		target.buffMinionWithoutMessage(0, 1, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Plate Armor", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public int getArmor(Board b ,Minion m)
    {
    	return 1;
    }
	
}
