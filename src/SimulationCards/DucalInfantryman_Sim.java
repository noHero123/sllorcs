package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class DucalInfantryman_Sim extends Simtemplate {
	//"id":121,"name":"Ducal Infantryman","description":"Other creatures you control on the same row have +1 Attack."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.position.row == own.position.row && m.position.column != own.position.column)
			{
				m.buffMinion(1, 0, 0, b);
				m.ducalInfCounter++;
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
			summonedMinion.buffMinion(1, 0, 0, b);
			summonedMinion.ducalInfCounter++;
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.position.row == auraendminion.position.row && m.position.column != auraendminion.position.column)
			{
				m.buffMinion(-1, 0, 0, b);
				m.ducalInfCounter--;
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
				if(field[i][j]!=null && field[i][j].typeId == 121)
				{
					royalcount++;
				}
			}
			
			for(int j=0; j<3; j++)
			{
				int change = 0;
				if(field[i][j]==null) continue;
				
				if(field[i][j].typeId == 121)change=-1;
				
				int diff = royalcount - field[i][j].ducalInfCounter + change;
				
				if(diff > 0)
				{
					//new ducal infantryman was added to row!
					field[i][j].buffMinion(diff, 0, 0, b);
					field[i][j].ducalInfCounter+=diff;
				}
				
				if(diff < 0)
				{
					//ducal moved away
					field[i][j].buffMinion(diff, 0, 0, b);
					field[i][j].ducalInfCounter+=diff;
				}
				
			}
			
		}
    	return;
    }
}
