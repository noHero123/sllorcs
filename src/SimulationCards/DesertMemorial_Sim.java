package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class DesertMemorial_Sim extends Simtemplate {
	//"id":109,"name":"Desert Memorial","description":"When Desert Memorial is destroyed, increase Energy by 1.",
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		b.changeMaxRessource(ResourceName.ENERGY, m.position.color, 1);
        return;
    }
	
	
}
