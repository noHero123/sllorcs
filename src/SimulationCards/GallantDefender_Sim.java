package SimulationCards;

import java.lang.management.MemoryType;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class GallantDefender_Sim extends Simtemplate {
	//"id":280,"name":"Gallant Defender","description":"As long as you have no more units than your opponent, Gallant Defender has [Armor] 2."
	//its using the current number of enemys (and should be simulated correctly):
	//testet 1vs1 situation , enemy had spiky1 + 1 hp -> gallant attacked- >enemy destroyed -> gallant got 1 spiky dmg! , 1v2 gallant got no dmg
	public int getArmor(Board b ,Minion triggerEffectMinion, Minion minion)
    {
		int numberown = b.getPlayerFieldList(minion.position.color).size();
		int numberenemy = b.getPlayerFieldList(Board.getOpposingColor(minion.position.color)).size();
		if(numberown<=numberenemy) return 2;
    	return 0;
    }

	

}
