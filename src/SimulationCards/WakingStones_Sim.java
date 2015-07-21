package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class WakingStones_Sim extends Simtemplate 
{

	//"id":61,"name":"Waking Stones","description":"All damage dealt to Waking Stones is reduced to 1."
	
	public boolean reduceDmgToOne(Board b ,Minion m)
    {
    	return true;
    }
	
}
