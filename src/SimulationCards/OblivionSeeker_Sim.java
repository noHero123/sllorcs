package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class OblivionSeeker_Sim extends Simtemplate {
	//"id":194,"name":"Oblivion Seeker","description":"When Oblivion Seeker is destroyed, draw 2 scrolls."

	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		
		b.drawCards(m.position.color, 2);
		
        return;
    }

	
}
