package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class UhuLongnose_Sim extends Simtemplate {
	//"id":363,"name":"Uhu Longnose","description":"When Uhu's Countdown is 0, you may reset its Countdown to increase [current] Energy by the number of Gravelocks you control."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		triggerEffectMinion.resetAcWithMessage(b);
		
		int energy =0;
		for(Minion mnn : b.getPlayerFieldList(triggerEffectMinion.position.color))
		{
			if(mnn.getSubTypes().contains(SubType.Gravelock)) energy++;
		}
		
		b.changeMaxRessource(ResourceName.ENERGY, triggerEffectMinion.position.color, energy);
		
        return;
    }
	

}
