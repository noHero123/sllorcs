package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.subType;

public class ChargeCoil_Sim extends Simtemplate {
	//"id":160,"name":"Charge Coil","description":"When Charge Coil attacks, a random opponent unit is dealt 1 [physical damage]."
	
	public  boolean hasSpecialAttack()
    {
        return true;
    }
	
	public  void doSpecialAttack(Board b, Minion own)
    {
		//special attack message is added automatically! dont have to do it here
		Color oppcol = Board.getOpposingColor(own.position.color);
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(oppcol));
		
		if(all.size() == 0) return;
		int randint = b.getRandomNumber(0, all.size()-1);
		
		
		b.doDmg(all.get(randint), own, 1, AttackType.UNDEFINED, DamageType.PHYSICAL);
		
        return;
    }
	
}
