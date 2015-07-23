package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class Crone_Sim extends Simtemplate {
	//"id":211,"name":"Crone","description":"Crone does not attack. When Crone is destroyed in combat, the attacking unit is destroyed."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		if(b.GameState != 1) return;// NOTE: gamestate = 1 -> we are in battle-phase

		if(attacker.position.color == m.position.color) return;
		if(attacker.cardType == Kind.CREATURE || attacker.cardType == Kind.STRUCTURE)
		{
			b.destroyMinion(attacker, m);
		}
			
		
        return;
    }
	
	
}
