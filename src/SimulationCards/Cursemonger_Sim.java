package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class Cursemonger_Sim extends Simtemplate {
	//"id":200,"name":"Cursemonger","description":"When Cursemonger's Countdown is 0, you may reset its Countdown to give target unit [Curse] 2."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		target.addnewCurse(b, triggerEffectMinion.position.color, 2);
		
		int ac = triggerEffectMinion.maxAc - triggerEffectMinion.getAc(); //ac should be 0
		triggerEffectMinion.buffMinion(0, 0, ac, b);
		
        return;
    }
	

}
