package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class KnightScholar_Sim extends Simtemplate {
	//"id":119,"name":"Knight Scholar","description":"When Knight Scholar's Countdown is 0, you may reset its Countdown to draw 1 scroll."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		
		
		b.drawCards(triggerEffectMinion.position.color, 1);
		triggerEffectMinion.buffMinion(0, 0, triggerEffectMinion.maxAc, b);
		
        return;
    }
	

}
