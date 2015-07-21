package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.subType;

public class DesertMemorial_Sim extends Simtemplate {
	//"id":109,"name":"Desert Memorial","description":"When Desert Memorial is destroyed, increase Energy by 1.",
	public  void onDeathrattle(Board b, Minion m)
    {
		b.changeMaxRessource(ResourceName.ENERGY, m.position.color, 1);
        return;
    }
	
	
}
