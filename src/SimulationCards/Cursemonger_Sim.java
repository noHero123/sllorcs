package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class Cursemonger_Sim extends Simtemplate {
	//"id":200,"name":"Cursemonger","description":"When Cursemonger's Countdown is 0, you may reset its Countdown to give target unit [Curse] 2."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		target.addnewCurse(b, triggerEffectMinion.position.color, 2);
		triggerEffectMinion.resetAcWithMessage(b);
		
        return;
    }
	

}
