package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class Stitcher_Sim extends Simtemplate {
	//"id":266,"name":"Stitcher","description":"When Stitcher's Countdown is 0, you may sacrifice target creature to give another target creature +2 Attack and +2 Health. If you do, Stitcher's Countdown is reset."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		Minion target1 = b.getMinionOnPosition(targets.get(0));
		Minion target2 = b.getMinionOnPosition(targets.get(1));
		
		triggerEffectMinion.resetAcWithMessage(b);
		b.destroyMinion(target1, triggerEffectMinion);
		target2.buffMinion(2, 2, 0, b);
		
		
        return;
    }
	

}
