package SimulationCards;

import java.lang.management.MemoryType;
import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class MiasmaWell_Sim extends Simtemplate {
	//"id":185,"name":"Miasma Well","description":"When Miasma Well's Countdown becomes 0, a random opponent creature becomes poisoned."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		//TODO do animation?
		ArrayList<Minion> creatures = new ArrayList<Minion>();
		for(Minion mnn : b.getPlayerFieldList(Board.getOpposingColor(m.position.color)))
		{
			if(mnn.cardType == Kind.CREATURE) creatures.add(mnn);
		}
		
		if(creatures.size() == 0) return;
		
		int randomint = b.getRandomNumber(0, creatures.size()-1);
		creatures.get(randomint).addnewPoison(b, m.position.color);
		
    	return;
    }
	
	
	
}
