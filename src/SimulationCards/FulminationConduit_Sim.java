package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.UPosition;

public class FulminationConduit_Sim extends Simtemplate {
	//"id":277,"name":"Fulmination Conduit","description":"Fulmination Conduit counts up instead of down at the beginning of its turn. You may sacrifice it to add its Countdown to your [current] Energy."

	public int doesCountDown(Board b, Minion m)
    {
        return 1; //-1 per round is default -> 0= dont count down, 1=count up (Fulmination Conduit) , -2 = counts down 2 per round etc...
    }
	
	//TODO can use ability with 0 ac?
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		
		b.changeCurrentRessource(ResourceName.ENERGY, triggerEffectMinion.position.color, triggerEffectMinion.getAc());
		
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
        return;
    }

}
