package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class WingsShield_Sim extends Simtemplate {
	//"id":206,"name":"Wings Shield","description":"Wings Shield has [Armor] equal to the number of units behind it."
	
	public int getArmor(Board b ,Minion triggerEffectMinion, Minion minion)
    {
		if(minion.position.column == 2) return 0;
		
		int beh = 0;
		for(int i = minion.position.column+1 ; i < 3; i++)
		{
			Minion behind = b.getMinionOnPosition(new UPosition(minion.position.color, minion.position.row, i));
			if(behind != null) beh++;
		}
		
    	return beh;
    }

	

}
