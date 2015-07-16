package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class Frostbeard_Sim extends Simtemplate {
	//id":91,"name":"Frostbeard","description":"When Frostbeard is destroyed during your opponent's turn, units you control get +2 Attack until end of your next turn."

	
	public  void onDeathrattle(Board b, Minion m)
    {
		
		if(b.activePlayerColor == m.position.color) return; //opponent turn!
		
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			if(mnn.getAc()>=0)
			{
				mnn.buffMinionWithoutMessage(2, 0, 0, b);
				mnn.addnewEnchantments("BUFF", "Frostbeard", m.card.cardDescription, m.card, b, m.position.color);
			}
		}
		
        return;
    }
	
	
	
}
