package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class HellspitterMortar_Sim extends Simtemplate {
	//"id":118,"name":"Hellspitter Mortar","description":"Attacks a randomly selected tile on opponent's board."

	public boolean hasSpecialAttackTarget()
    {
    	return true;
    }
	
	public ArrayList<Position> getSpecialAttackTarget(Board b, Minion m)
    {
		//TODO: hellspitter is targeting field with minions at a higher rate?
    	ArrayList<Position> posis = new ArrayList<Position>();
    	Color c = Board.getOpposingColor(m.position.color);
    	int row = b.getRandomNumber(0, 4);
    	int colu = b.getRandomNumber(0, 2);
    	posis.add(new Position(c, row, colu));
    	return posis;
    }
}
