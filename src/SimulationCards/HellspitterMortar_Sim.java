package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class HellspitterMortar_Sim extends Simtemplate {
	//"id":118,"name":"Hellspitter Mortar","description":"Attacks a randomly selected tile on opponent's board."

	public boolean hasSpecialAttackTarget()
    {
    	return true;
    }
	
	public ArrayList<UPosition> getSpecialAttackTarget(Board b, Minion m)
    {
		//TODO: hellspitter is targeting field with minions at a higher rate?
    	ArrayList<UPosition> posis = new ArrayList<UPosition>();
    	UColor c = Board.getOpposingColor(m.position.color);
    	int row = b.getRandomNumber(0, 4);
    	int colu = b.getRandomNumber(0, 2);
    	posis.add(new UPosition(c, row, colu));
    	return posis;
    }
}
