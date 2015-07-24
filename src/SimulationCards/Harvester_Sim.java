package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Harvester_Sim extends Simtemplate {
	//"id":172,"name":"Harvester","description":"Does not count down. When a creature is destroyed, Harvester's Countdown is decreased by 1."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
	public  boolean doesCountDown(Board b, Minion m)
    {
        return false;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		triggerEffectMinion.buffMinion(0, 0, -1, b);
        return;
    }
	
}
