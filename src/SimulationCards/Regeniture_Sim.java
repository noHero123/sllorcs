package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Regeniture_Sim extends Simtemplate 
{

	//"id":191,"name":"Regeniture","description":"Enchanted unit gets +2 Health. When Regeniture comes into play, the idol behind it is dealt 1 damage."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 2, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Regeniture", playedCard.card.cardDescription, playedCard, b);
		
		Minion idol = b.getPlayerIdol(target.position.color, target.position.row);
		b.doDmg(idol, playedCard, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		
        return;
    }
	
}
