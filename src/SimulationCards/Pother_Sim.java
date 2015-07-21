package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class Pother_Sim extends Simtemplate {
	//"id":57,"name":"Pother","description":"Target unit is moved to a random adjacent tile. Draw 1 scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		
		Minion m = b.getMinionOnPosition(targets.get(0));
		if(m != null)
		{
			Color opp = Board.getOpposingColor(b.activePlayerColor);
			Minion[][] enemyb = b.getPlayerField(opp);
			//move unit to random tile
			ArrayList<Position> nbrs  = m.position.getNeightbours();
			ArrayList<Position> freetiles  = new ArrayList<Position>();
			for(Position ps : nbrs)
			{
				if(enemyb[ps.row][ps.column] == null)
				{
					freetiles.add(ps);
				}
			}
			if(freetiles.size() >= 1)
			{
			
				int randomint = b.getRandomNumber(0, freetiles.size() - 1);
				b.unitChangesPlace(m.position, freetiles.get(randomint), true, true);	
			}
			
		}
		
		b.drawCards(playedCard.position.color, 1);
		
		
        return;
    }
	
}
