package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class Echomaton_Sim extends Simtemplate {
	//"id":284,"name":"Echomaton","description":"When an opponent plays a spell, Echomaton gets +2 Attack. When Echomaton is destroyed, if Attack is 3 or more, increase Energy by 1."

	public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
    {
		if(triggerEffectMinion.position.color == spell.position.color) return;
		
		triggerEffectMinion.buffMinion(2, 0, 0, b);
    	return;
    }

	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		if(m.getAttack(b)>=3)
		{
			b.changeMaxRessource(ResourceName.ENERGY, m.position.color, 1);
		}
        return;
    }
}
