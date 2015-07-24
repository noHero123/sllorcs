package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Redeploy_Sim extends Simtemplate
{
	
	//"id":230,"name":"Redeploy","description":"Swap two target rows on your side."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_all;
	}
	
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.own_all;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		int row1 = targets.get(0).row;
		int row2 = targets.get(1).row;
		UColor c = playedCard.position.color;
		
		
		//first create message
		String teleports="";
		for(int i=0; i<3; i++)
		{
			UPosition c1=new UPosition(c, row1, i);
			UPosition c2=new UPosition(c, row2, i);
			Minion m1 = b.getMinionOnPosition(c1);
			Minion m2 = b.getMinionOnPosition(c2);
			if(m1!=null)
			{
				//{\"from\":"+targets.get(0)+",\"to\":"+targets.get(1)+"}
				if(!teleports.equals("")) teleports+=",";
				teleports+="{\"from\":"+c1.posToString()+",\"to\":"+c2.posToString()+"}";
			}
			if(m2!=null)
			{
				//{\"from\":"+targets.get(0)+",\"to\":"+targets.get(1)+"}
				if(!teleports.equals("")) teleports+=",";
				teleports+="{\"from\":"+c2.posToString()+",\"to\":"+c1.posToString()+"}";
			}
		}
		
		String s = "{\"TeleportUnits\":{\"units\":["+teleports+"]}}";
		b.addMessageToBothPlayers(s);
		//switch units
		for(int i=0; i<3; i++)
		{
			UPosition c1=new UPosition(c, row1, i);
			UPosition c2=new UPosition(c, row2, i);
			Minion m1 = b.getMinionOnPosition(c1);
			Minion m2 = b.getMinionOnPosition(c2);
			if(m1==null && m2==null) 
			{
				continue;
			}
			b.unitChangesPlace(c1, c2, true, false);
		}
		
		
        
        return;
    }
	
}
