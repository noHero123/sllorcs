package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class SolemnGiant_Sim extends Simtemplate {
	//"id":204,"name":"Solemn Giant","description":"Does not count down. Pay 2 Energy to decrease Countdown by 2."
	
	public  boolean doesCountDown(Board b, Minion m)
    {
        return false;
    }
    
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
    	return;
    }
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		triggerEffectMinion.buffMinion(0, 0, -2, b);
        return;
    }
	
	
	
}
