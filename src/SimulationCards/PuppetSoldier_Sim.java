package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class PuppetSoldier_Sim extends Simtemplate {
	//"id":169,"name":"Puppet Soldier","description":"When Puppet Soldier is destroyed in combat, all units on the same row are destroyed."
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		if(b.GameState != 1) return;// NOTE: gamestate = 1 -> we are in battle-phase

		int row = m.position.row;
		for(Minion mnn: b.getAllMinionOfField())
		{
			if(mnn.position.row == row)
			{
				//destroy!
				b.destroyMinion(mnn, m);
			}
		}
		
        return;
    }
	
	
}
