package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class WingsSorceress_Sim extends Simtemplate {
	//"id":360,"name":"Wings Sorceress","description":"When Wings Sorceress's Countdown is 0, you may reset its Countdown to remove all enchantments and effects from target unit."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		triggerEffectMinion.resetAcWithMessage(b);
		
		Minion target = b.getMinionOnPosition(targets.get(0));

		
		for(Minion m : target.getAttachedCards())
		{
			m.owner.removeEnchantment(m, false, b);
		}
		
        return;
    }
	

	
	
}
