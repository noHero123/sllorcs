package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Reversal_Sim extends Simtemplate
{
	
	//"id":275,"name":"Reversal","description":"Frontmost and rearmost units on one side of target row switch places. Draw 1 scroll."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		UColor c = targets.get(0).color;
		int row = targets.get(0).row;
		//one of these positions is at least the middle :D
		UPosition p0 = new UPosition(c, row, 1);
		UPosition p1 = new UPosition(c, row, 1);
		boolean middleIsNOminion =true;
		if(b.getMinionOnPosition(p0) != null)
		{
			middleIsNOminion=false;
		}
		UPosition front = new UPosition(c, row, 0);
		UPosition rear = new UPosition(c, row, 2);
		if(b.getMinionOnPosition(front) != null)
		{
			p0=front;
		}
		if(b.getMinionOnPosition(rear) != null)
		{
			p1=rear;
		}
		//there is no movement, when:
		// in the middle lane is no minion, and p0 or p1 is still the middle (then there is only on minion in that row), OR p0 == p1 (there is only one minion, and thats the middle)
		if( (middleIsNOminion && (p0.column ==1 || p1.column==1)) || p0.column == p1.column )
		{
			b.drawCards(playedCard.position.color, 1);
			return;
		}
		
		String s = "{\"TeleportUnits\":{\"units\":[{\"from\":"+p0.posToString()+",\"to\":"+p1.posToString()+"},{\"from\":"+p1.posToString()+",\"to\":"+p0.posToString()+"}]}}";
		b.addMessageToBothPlayers(s);
		b.unitChangesPlace(target.position, targets.get(1), true, false);
		
        b.drawCards(playedCard.position.color, 1);
        return;
    }
	
}
