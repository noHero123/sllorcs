package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class WingsCleaver_Sim extends Simtemplate {
	//"id":306,"name":"Wings Cleaver","description":""
	// relentless
	//dominion = +3 attack + base countdown  decreased by 1
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
	{
		own.turnCounter=0;
		for(Minion idol : b.getPlayerIdols(Board.getOpposingColor(own.position.color)))
		{
			if(own.turnCounter==0 && idol.Hp<=0)
			{
				own.turnCounter=1;
				 
				own.maxAc-=1;
				own.buffMinion(3, 0, 0, b);
			}
		}
		
    }
	
	 public boolean isRelentless(Board b ,Minion m)
	 {
		 return true;
	 }
	 
	
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(!diedMinion.isIdol || triggerEffectMinion.turnCounter>=1) return;
		 triggerEffectMinion.turnCounter=1;
		 
		 triggerEffectMinion.maxAc-=1;
		 triggerEffectMinion.buffMinion(3, 0, 0, b);
		 
	      return;
	 }
	 
}
