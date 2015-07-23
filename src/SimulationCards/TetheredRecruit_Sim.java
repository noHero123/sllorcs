package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class TetheredRecruit_Sim extends Simtemplate {
	//"id":131,"name":"Tethered Recruit","description":"When Tethered Recruit is destroyed, increase Order by 1."
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		b.changeMaxRessource(ResourceName.ORDER, m.position.color, 1);
        return;
    }
	
	
}
