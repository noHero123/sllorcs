package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class TerreneBrute_Sim extends Simtemplate {
	//"id":374,"name":"Terrene Brute","description":"When a creature comes into play on your side, Terrene Brute's Countdown is decreased by 1."

	
	 
	    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
	    	if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.cardType == Kind.CREATURE)
	    	{
	    		triggerEffectMinion.buffMinion(0, 0, -1, b);
	    	}
	        return;
	    }

}
