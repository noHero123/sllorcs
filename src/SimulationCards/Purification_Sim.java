package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Purification_Sim extends Simtemplate
{
	//"id":99,"name":"Purification","description":"Remove all enchantments and [effects] from target unit."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	//TODO make it right?
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {

		Minion target = b.getMinionOnPosition(targets.get(0));
		ArrayList<Minion> grave = b.getPlayerGrave(target.position.color);
		for(Minion m : target.getAttachedCards())
		{
			m.owner.removeEnchantment(m, false, b);
		}

        return;
    }
	
	
	
}
