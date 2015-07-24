package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class EtherPump_Sim extends Simtemplate {
	//"id":82,"name":"Ether Pump","description":"When Ether Pump attacks, opponent's units are dealt 1 [physical damage]."
	
	public  boolean hasSpecialAttack()
    {
        return true;
    }
	
	public  void doSpecialAttack(Board b, Minion own)
    {
		//special attack message is added automatically! dont have to do it here
		UColor oppcol = Board.getOpposingColor(own.position.color);
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(oppcol));
		
		for(Minion m : all)
		{
			m.aoeDmgToDo=1;
		}
		
		b.doDmg(all, own, -100, AttackType.UNDEFINED, DamageType.PHYSICAL);
		
        return;
    }
	
}
