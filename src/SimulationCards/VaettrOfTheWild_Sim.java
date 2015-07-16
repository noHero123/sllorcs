package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class VaettrOfTheWild_Sim extends Simtemplate {
	//"id":156,"name":"Vaettr of the Wild","description":"While Vaettr of the Wild is in play, Growth is increased by 1."
	//burn = 27 to test :D

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int[] curress = b.whiteRessources;
		if(own.position.color == Color.black)
		{
			curress= b.blackRessources;
		}
		curress[0]+=1;//growth +1
		//send ressourceupdates message
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
        return;
    }
	
	
	public void onMinionLeavesBattleField(Board b, Minion auraendminion)
	{
		int[] curress = b.whiteRessources;
		if(auraendminion.position.color == Color.black)
		{
			curress= b.blackRessources;
		}
		curress[0]-=1;//growth +1
		//send ressourceupdates message
		if(auraendminion.turnsInplay>=1)
		{
			//reduce current
			curress = b.whitecurrentRessources;
			if(auraendminion.position.color == Color.black)
			{
				curress= b.blackcurrentRessources;
			}
		}
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
	}
	
}
