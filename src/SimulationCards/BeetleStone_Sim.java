package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class BeetleStone_Sim extends Simtemplate {
	//"id":259,"name":"Beetle Stone","description":"When another Beetle Stone comes into play, 2 damage is dealt to a random opponent idol. Beetle Stone is then placed at the top of your library."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m: b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId!=259) continue;
			
			b.addMinionFromFieldToDeck(m);
			
			//do dmg to random idol
			ArrayList<Minion> idols = new ArrayList<Minion>();
			UColor oppcol = Board.getOpposingColor(own.position.color);
			for(Minion idol : b.getPlayerIdols(oppcol))
			{
				if(idol.Hp>=1) idols.add(idol);
			}
			if( idols.size()==0) continue;
		
			int randomint = b.getRandomNumber(0, idols.size()-1);
					
			b.doDmg(b.getPlayerIdol(oppcol, randomint), own, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
			
		}
		
    }
	

}
