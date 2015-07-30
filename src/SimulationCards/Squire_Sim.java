package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Squire_Sim extends Simtemplate {
	//"id":358,"name":"Squire","description":"Other creatures you control on the same row count as Knights."

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(own.position.color)))
		{
			if(m.position.row == own.position.row && m != own)
			{
				m.addSubtype(SubType.Knight, b);
				m.squireCounter++;
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color) return; //only buff opp. minions

		if(summonedMinion.position.row == triggerEffectMinion.position.row)
		{
			summonedMinion.addSubtype(SubType.Knight, b);
			summonedMinion.squireCounter++;
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(auraendminion.position.color)))
		{
			if(m.position.row == auraendminion.position.row )
			{
				m.removeSubtype(SubType.Knight, b);
				m.squireCounter--;
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
				if(ownfield[i][j]!=null && ownfield[i][j].typeId == 358)
				{
					royalcount++;
				}
			}
			
			for(int j=0; j<3; j++)
			{
				int change = 0;
				if(field[i][j]==null) continue;
				int diff = royalcount - field[i][j].squireCounter;
				
				if(diff > 0)
				{
					//new squire was added to row!
					for(int ii=0 ; ii < diff; ii++ )
					{
						field[i][j].addSubtype(SubType.Knight, b);
					}
					field[i][j].squireCounter+=diff;
				}
				
				if(diff < 0)
				{
					//a squire is gone of this row... remove subtypes...
					for(int ii=0 ; ii < -diff; ii++ )
					{
						field[i][j].removeSubtype(SubType.Knight, b);
					}
					
					field[i][j].squireCounter+=diff;
				}
				
			}
			
		}
    	return;
    }
	

}
