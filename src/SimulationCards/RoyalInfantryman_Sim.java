package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class RoyalInfantryman_Sim extends Simtemplate {
	//"id":126,"name":"Royal Infantryman","description":"Other creatures you control on the same row have +1 Health."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.position.row == own.position.row && m.position.column != own.position.column)
			{
				m.buffMinion(0, 1, 0, b);
				m.royalInfCounter++;
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		
		if(summonedMinion.position.row == triggerEffectMinion.position.row && summonedMinion.position.column != triggerEffectMinion.position.column)
		{
			summonedMinion.buffMinion(0, 1, 0, b);
			summonedMinion.royalInfCounter++;
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.position.row == auraendminion.position.row && m.position.column != auraendminion.position.column)
			{
				m.buffMinion(0, -1, 0, b);
				m.royalInfCounter--;
			}
		}
        return;
    }
	
	
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		Minion[][] field = b.getPlayerField(triggerEffectMinion.position.color);
		
		for(int i=0; i<5; i++)
		{
			int royalcount = 0;
			for(int j=0; j<3; j++)
			{
				if(field[i][j]!=null && field[i][j].typeId == 126)
				{
					royalcount++;
				}
			}
			
			for(int j=0; j<3; j++)
			{
				int change = 0;
				if(field[i][j]==null) continue;
				
				if(field[i][j].typeId == 126)change=-1;
				
				int diff = royalcount - field[i][j].royalInfCounter + change;
				
				if(diff != 0)
				{
					System.out.println(field[i][j].position.posToString() + ": " + royalcount + ": " +  field[i][j].royalInfCounter);
				}
				
				if(diff > 0)
				{
					//new royal infantryman was added to row!
					field[i][j].buffMinion(0, diff, 0, b);
					field[i][j].royalInfCounter+=diff;
				}
				
				if(diff < 0)
				{
					//royal moved away
					field[i][j].buffMinion(0, diff, 0, b);
					field[i][j].royalInfCounter+=diff;
				}
				
			}
			
		}
    	return;
    }
}
