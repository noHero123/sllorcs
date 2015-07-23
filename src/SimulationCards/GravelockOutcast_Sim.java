package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class GravelockOutcast_Sim extends Simtemplate {
	//"id":130,"name":"Gravelock Outcast","description":"Pay 1 Growth to fully [heal] Gravelock Outcast."
	// GrowthRegenerateAbility
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		int heal = triggerEffectMinion.maxHP - triggerEffectMinion.Hp;
		
		triggerEffectMinion.healMinion(heal, b);
		
        return;
    }
	

}
