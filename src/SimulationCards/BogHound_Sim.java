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
		for(Minion idol : b.getPlayerIdols(Board.getOpposingColor(own.position.color)))
		{
			if(own.turnCounter==0 && idol.Hp<=0)
			{
				own.turnCounter=1;
				own.buffMinion(3, 0, 0, b);
			}
		}
		
    }
	 
	
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(diedMinion.isIdol && triggerEffectMinion.turnCounter==0)
		 {
			 triggerEffectMinion.turnCounter=1;
			 triggerEffectMinion.buffMinion(3, 0, 0, b);
		 }
		 
	      return;
	 }
	 
}
