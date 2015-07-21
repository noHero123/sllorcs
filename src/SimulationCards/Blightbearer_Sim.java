package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.subType;

public class Blightbearer_Sim extends Simtemplate {
	//"id":168,"name":"Blightbearer","description":"Blightbearer takes 1 [poison damage] before attacking. When Blightbearer is destroyed, all creatures on the same row become [poisoned]."

	
	public  void onDeathrattle(Board b, Minion m)
    {
		
		int row = m.position.row;
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			if(mnn.position.row == row && m.Hp>=1)
			{
				mnn.addnewPoison(b, m.position.color);
			}
		}
		
        return;
    }
	
	public  void onUnitIsGoingToAttack(Board b, Minion triggerEffectMinion, Minion attacker )
    {
		if(triggerEffectMinion!=attacker) return;
		b.doDmg(attacker, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.POISON);
        return;
    }

	
}
