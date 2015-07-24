package SimulationCards;

import java.lang.management.MemoryType;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class RighteousPartisan_Sim extends Simtemplate {
	//"id":308,"name":"Righteous Partisan","description":"When Righteous Partisan comes into play, if you have no more units than your opponent, draw 1 scroll."

	//tested: it is counting to the minoins.. so we add +1 
	//note battlecry is done BEFORE that unit is placed
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int numberown = b.getPlayerFieldList(own.position.color).size() +1;//+1 for righteous partisan
		int numberenemy = b.getPlayerFieldList(Board.getOpposingColor(own.position.color)).size();
		if(numberown<=numberenemy)
		{
			b.drawCards(own.position.color, 1);
		}
        return;
    }
	
	
	

}
