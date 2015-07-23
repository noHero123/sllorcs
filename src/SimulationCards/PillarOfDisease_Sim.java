package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class PillarOfDisease_Sim extends Simtemplate {
	//"id":170,"name":"Pillar of Disease","description":"Opponent units on the same row have -1 Attack."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(own.position.color)))
		{
			if(m.position.row == own.position.row)
			{
				m.buffMinion(-1, 0, 0, b);
				m.pillarCounter++;
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color) return; //only buff opp. minions

		if(summonedMinion.position.row == triggerEffectMinion.position.row)
		{
			summonedMinion.buffMinion(-1, 0, 0, b);
			summonedMinion.pillarCounter++;
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(auraendminion.position.color)))
		{
			if(m.position.row == auraendminion.position.row )
			{
				m.buffMinion(+1, 0, 0, b);
				m.pillarCounter--;
			}
		}
        return;
    }
	
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		Minion[][] field = b.getPlayerField(Board.getOpposingColor(triggerEffectMinion.position.color));
		Minion[][] ownfield = b.getPlayerField(triggerEffectMinion.position.color);
		
		for(int i=0; i<5; i++)
		{
			int royalcount = 0;
			for(int j=0; j<3; j++)
			{
				if(ownfield[i][j]!=null && ownfield[i][j].typeId == 170)
				{
					royalcount++;
				}
			}
			
			for(int j=0; j<3; j++)
			{
				int change = 0;
				if(field[i][j]==null) continue;
				int diff = royalcount - field[i][j].pillarCounter;
				
				if(diff > 0)
				{
					//new ducal infantryman was added to row!
					field[i][j].buffMinion(-diff, 0, 0, b);
					field[i][j].pillarCounter+=diff;
				}
				
				if(diff < 0)
				{
					//ducal moved away
					field[i][j].buffMinion(-diff, 0, 0, b);
					field[i][j].pillarCounter+=diff;
				}
				
			}
			
		}
    	return;
    }
}
