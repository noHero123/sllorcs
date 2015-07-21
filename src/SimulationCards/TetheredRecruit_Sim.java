package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.subType;

public class TetheredRecruit_Sim extends Simtemplate {
	//"id":131,"name":"Tethered Recruit","description":"When Tethered Recruit is destroyed, increase Order by 1."
	public  void onDeathrattle(Board b, Minion m)
    {
		b.changeMaxRessource(ResourceName.ORDER, m.position.color, 1);
        return;
    }
	
	
}
