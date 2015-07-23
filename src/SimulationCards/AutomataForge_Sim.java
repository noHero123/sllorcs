package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class AutomataForge_Sim extends Simtemplate {
	//"id":207,"name":"Automata Forge","description":"Instead of attacking, Automata Forge summons a <Gun Automaton>\\non an adjacent tile. Pay 3 Energy to decrease Countdown by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
    	return;
    }
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		triggerEffectMinion.buffMinion(0, 0, -1, b);
        return;
    }
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		if(m!=self) return;
		//choose random target
		ArrayList<Position> postargets = b.getFreePositionsFromPosition(m.position.getNeightbours());
		if(postargets.size()==0) return;
		
		int random = b.getRandomNumber(0, postargets.size()-1);
		
		Position targ = postargets.get(random);
		
		Card c = CardDB.getInstance().cardId2Card.get(96);
		Minion ill = new Minion(c, -1, m.position.color);
		b.summonUnitOnPosition(targ, ill);
        return;
    }
	
	
	
}
