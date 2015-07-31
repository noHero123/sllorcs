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

public class EarthbornKeeper_Sim extends Simtemplate {
	//"id":372,"name":"Earthborn Keeper","description":"When a unit comes into play on your side, Earthborn Keeper gets +1 Attack until end of turn."

	
	 
	    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
	    	if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion != triggerEffectMinion)
	    	{
	    		
	    		triggerEffectMinion.buffMinion(1, 0, 0, b);
	    		triggerEffectMinion.turnCounter++;
	    	}
	        return;
	    }
	    
		
		
		public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	    {
			triggerEffectMinion.buffMinion(-triggerEffectMinion.turnCounter, 0, 0, b);
			triggerEffectMinion.turnCounter=0;
	        return false;//its not a buff
	    }
	    

}
