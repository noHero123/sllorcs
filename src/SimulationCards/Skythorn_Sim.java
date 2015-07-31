package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Skythorn_Sim extends Simtemplate {
	//"id":321,"name":"Skythorn","description":"Does not count down. When a creature comes into play on your side, Skythorn's Countdown is decreased by 1. When Skythorn's Countdown becomes 0, draw 1 scroll."
	
	public int doesCountDown(Board b, Minion m)
    {
        return 0; //-1 per round is default -> 0= dont count down, 1=count up (Fulmination Conduit)
    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {

    	return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(triggerEffectMinion.position.color == summonedMinion.position.color && summonedMinion.cardType == Kind.CREATURE)
		{
			triggerEffectMinion.buffMinion(0, 0, -1, b);
		}
        return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		b.drawCards(m.position.color, 1);
    	return;
    }
	
	public int getSpikyDamage(Board b ,Minion m, Minion defender)
    {
    	return 1;
    }
	
	
}
