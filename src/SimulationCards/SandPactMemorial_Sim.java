package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class SandPactMemorial_Sim extends Simtemplate {
	//"id":112,"name":"Sand Pact Memorial","description":"When Sand Pact Memorial comes into play, increase [current] Energy by 5."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		b.changeCurrentRessource(ResourceName.ENERGY, own.position.color, 5);
        return;
    }
	
	
}
