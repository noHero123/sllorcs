package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class ToolInitiate_Sim extends Simtemplate {
	//"id":150,"name":"Tool Initiate","description":"When Tool Initiate's Countdown is 0, you may increase its Countdown by 1 to give target structure +1 Attack."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinion(1, 0, 0, b);//TODO is it an enchantment?
		triggerEffectMinion.buffMinion(0, 0, 1, b);
		
        return;
    }
	

}
