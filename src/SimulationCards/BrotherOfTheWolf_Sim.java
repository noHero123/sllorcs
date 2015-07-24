package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class BrotherOfTheWolf_Sim extends Simtemplate {
	//"id":22,"name":"Brother of the Wolf","description":"When Brother of the Wolf's Countdown is 0, you may increase Countdown by 2 to summon a\\n<Ragged Wolf> on an adjacent tile."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		UPosition targ = targets.get(0);
		
		Card c = CardDB.getInstance().cardId2Card.get(49);
		Minion ill = new Minion(c, -1, triggerEffectMinion.position.color);
		
		b.summonUnitOnPosition(targ, ill);
		
		triggerEffectMinion.buffMinion(0, 0, 2, b);
		
        return;
    }
	

}
