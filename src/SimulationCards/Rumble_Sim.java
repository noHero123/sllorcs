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

public class Rumble_Sim extends Simtemplate {
	//"id":76,"name":"Rumble","description":"Each opponent unit is moved to a random adjacent tile.",
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		Color opp = Board.getOpposingColor(b.activePlayerColor);
		Minion[][] enemyb = b.getPlayerField(opp);
		//we use aoeDmgToDo=1 as flag if unit moved or not;
		for(Minion m : b.getPlayerFieldList(opp))
		{
			m.aoeDmgToDo=1;
		}
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = enemyb[i][j];
				if(m != null && m.aoeDmgToDo==1)
				{
					m.aoeDmgToDo=0;
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
					if(freetiles.size() == 0) continue;
					
					int randomint = b.getRandomNumber(0, freetiles.size() - 1);
					b.unitChangesPlace(m.position, freetiles.get(randomint), false);		
					
				}
				
			}
		}
		b.doOnFieldChangedTriggers();
		
		
        return;
    }
	
}
