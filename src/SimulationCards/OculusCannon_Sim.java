package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class OculusCannon_Sim extends Simtemplate {
	//"id":322,"name":"Oculus Cannon","description":"Does not count down. When an opponent unit moves to a tile on the same row as Oculus Cannon, Oculus Cannon's Countdown is set to 0."
	//piercing + ranged
	
	public boolean hasPiercing(Board b ,Minion m)
    {
    	return true;
    }
	
	public int doesCountDown(Board b, Minion m)
    {
        return 0; //-1 per round is default -> 0= dont count down, 1=count up (Fulmination Conduit)
    }
	//quake and stuff does also work ! (tested)
	
	public  void onMinionMoved(Board b, Minion triggerEffectMinion, Minion movedMinion)
    {
		if(triggerEffectMinion.position.color != movedMinion.position.color && triggerEffectMinion.position.row == movedMinion.position.row)
		{
			int ac = triggerEffectMinion.getAc();
			if( ac >=1)
			{
				triggerEffectMinion.buffMinion(0, 0, -ac, b);
			}
		}
        return;
    }

}
