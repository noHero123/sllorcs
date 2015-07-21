package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.subType;

public class VaettrOfTheWild_Sim extends Simtemplate {
	//"id":156,"name":"Vaettr of the Wild","description":"While Vaettr of the Wild is in play, Growth is increased by 1."
	//burn = 27 to test :D

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		b.changeMaxRessource(ResourceName.GROWTH, own.position.color, 1);
        return;
    }
	
	
	public void onMinionLeavesBattleField(Board b, Minion auraendminion)
	{
		b.changeMaxRessource(ResourceName.GROWTH, auraendminion.position.color, -1);
		//send ressourceupdates message
		if(auraendminion.turnsInplay>=1)
		{
			b.changeCurrentRessource(ResourceName.GROWTH, auraendminion.position.color, -1);
		}
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
	}
	
}
