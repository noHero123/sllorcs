package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Ruse_Sim extends Simtemplate {
	//"id":313,"name":"Ruse","description":"Units you control have [Spiky] 2."
	//linger 3

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 3;
	 }
	 
	 public int getSpikyDamage(Board b ,Minion m, Minion defender)
	 {
		 if(m.position.color == defender.position.color) return 2;
	    
		 return 0;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		b.addRule(playedCard);
	    return;
	 }
	 
	 

}
