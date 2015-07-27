package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class Revenant_Sim extends Simtemplate {
	//"id":349,"name":"Revenant","description":"Revenant is destroyed after attacking."
	//ward
    public boolean hasWard(Board b ,Minion m)
    {
    	return true;
    }
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		if(m.getAc() != 0 || m!=self)
        {
			//System.out.println("ap " + m.getAc() + " " );
			return;
        }
		
		b.destroyMinion(m, m);
		
    	return;
    }

}
