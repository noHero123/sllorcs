package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class Noaidi_Sim extends Simtemplate 
{

	//"id":203,"name":"Noaidi","description":"" replenish
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		//replenish
		int x = own.card.costGrowth + own.card.costOrder + own.card.costEnergy + own.card.costDecay;
		b.changeCurrentRessource(ResourceName.WILD, own.position.color, x);
        return;
    }
	
	
	
}
