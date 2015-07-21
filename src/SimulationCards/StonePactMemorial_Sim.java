package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.subType;

public class StonePactMemorial_Sim extends Simtemplate {
	//"id":113,"name":"Stone Pact Memorial","description":"When Stone Pact Memorial comes into play, increase Order by 1."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		b.changeMaxRessource(ResourceName.ORDER, own.position.color, 1);
        return;
    }
	
	
}
