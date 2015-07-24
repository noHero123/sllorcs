package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class MysticAltar_Sim extends Simtemplate {
	//"id":229,"name":"Mystic Altar","description":"When Countdown is 0, you may reset its Countdown and increase Countdown of target creature you control by 2 to draw 1 scroll."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		triggerEffectMinion.resetAcWithMessage(b);
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinion(0, 0, 2, b);
		
		b.drawCards(triggerEffectMinion.position.color, 1);
		
        return;
    }
	

}
