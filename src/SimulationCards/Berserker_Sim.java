package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Berserker_Sim extends Simtemplate
{
	//""id":217,"name":"Berserker","description":"When Berserker takes damage, Berserker's gets +1 Attack."
	
	public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
    {
		if(damagedMinion != triggerEffectMinion || dmg <=0) return;
		triggerEffectMinion.buffMinion(1, 0, 0, b);
        return;
    }
	

	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
	{
		if(turnStartColor == triggerEffectMinion.position.color)
		{
			triggerEffectMinion.healMinion(1,b);
		}
	}
	
}
