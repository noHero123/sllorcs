package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class BogHound_Sim extends Simtemplate {
	//"id":269,"name":"Bog Hound","description":""
	//dominion = +3 attack
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
	{
		own.turnCounter=0;
		if(b.isDominionActive(own.position.color))
		{
			own.turnCounter=1;
			own.buffMinion(3, 0, 0, b);
		}
		
    }
	
	public void onDominonOccours(Board b , Minion triggerEffectMinion)
    {
		if(triggerEffectMinion.turnCounter==0)
		 {
			 triggerEffectMinion.turnCounter=1;
			 triggerEffectMinion.buffMinion(3, 0, 0, b);
		 }
    	return;
    }
	
	public void onDominonGoesAway(Board b , Minion triggerEffectMinion)
    {
		if(triggerEffectMinion.turnCounter==1)
		 {
			 triggerEffectMinion.turnCounter=0;
			 triggerEffectMinion.buffMinion(-3, 0, 0, b);
		 }
    	return;
    }
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(diedMinion.isIdol && diedMinion.position.color != triggerEffectMinion.position.color && triggerEffectMinion.turnCounter==0)
		 {
			 triggerEffectMinion.turnCounter=1;
			 triggerEffectMinion.buffMinion(3, 0, 0, b);
		 }
		 
	      return;
	 }
	 
}
