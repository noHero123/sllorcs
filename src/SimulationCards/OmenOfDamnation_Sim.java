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

public class OmenOfDamnation_Sim extends Simtemplate {
	//"id":540,"name":"Omen of Damnation","description":"When an opponent creature comes into play, a random idol on that side is dealt 1 damage."
	//linger 4

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 4;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		b.addRule(playedCard);
	    return;
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
		 	if(summonedMinion.position.color == triggerEffectMinion.position.color) return;
		 	
		 	ArrayList<Minion> idols = new ArrayList<Minion>();
			UColor oppcol = Board.getOpposingColor(triggerEffectMinion.position.color);
			for(Minion idol : b.getPlayerIdols(oppcol))
			{
				if(idol.Hp>=1) idols.add(idol);
			}
			if( idols.size()==0) return;
		
			int randomint = b.getRandomNumber(0, idols.size()-1);
					
			b.doDmg(b.getPlayerIdol(oppcol, randomint), triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
	        return;
	    }

}
