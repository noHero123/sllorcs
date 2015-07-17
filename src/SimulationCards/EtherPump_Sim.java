package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.subType;

public class EtherPump_Sim extends Simtemplate {
	//"id":82,"name":"Ether Pump","description":"When Ether Pump attacks, opponent's units are dealt 1 [physical damage]."
	
	public  boolean hasSpecialAttack()
    {
        return true;
    }
	
	public  void doSpecialAttack(Board b, Minion own)
    {
		//special attack message is added automatically! dont have to do it here
		
		ArrayList<Minion> all = new ArrayList<Minion>(b.getAllMinionOfField());
		
		b.doDmg(all, own, 1, AttackType.UNDEFINED, DamageType.PHYSICAL);
		
        return;
    }
	
}
