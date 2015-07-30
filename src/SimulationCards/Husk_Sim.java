package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Husk_Sim extends Simtemplate {
	//"id":163,"name":"Husk","description":""
	
	//we use turncounter=99 do destroy after attacking (nuru flesh seamstress)
	public void onAttackDone(Board b , Minion m, Minion self)
	 {
		
		 if(self.turnCounter != 99) return;
		 
			if(m.getAc() != 0 || m!=self)
	        {
				return;
	        }
			
			m.turnCounter=0;
			b.destroyMinion(m, self);
			
	    	return;
	 }
	

}
