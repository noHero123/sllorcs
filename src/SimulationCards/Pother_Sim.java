package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Pother_Sim extends Simtemplate {
	//"id":57,"name":"Pother","description":"Target unit is moved to a random adjacent tile. Draw 1 scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		
		Minion m = b.getMinionOnPosition(targets.get(0));
		if(m != null)
		{
			UColor opp = m.position.color;
			Minion[][] enemyb = b.getPlayerField(opp);
			//move unit to random tile
			ArrayList<UPosition> nbrs  = m.position.getNeightbours();
			ArrayList<UPosition> freetiles  = new ArrayList<UPosition>();
			for(UPosition ps : nbrs)
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
