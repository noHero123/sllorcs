package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class OumLasaHighGuard_Sim extends Simtemplate {
	//""id":381,"name":"Oum Lasa High Guard","description":"Any unit facing Oum Lasa High Guard with no units in between gets -3 Attack and has [Move] 0."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		Minion targ = null;
		
		Minion[][] field = b.getPlayerField(Board.getOpposingColor(own.position.color));
		
		for(int i=0; i< 3; i++)
		{
			Minion m = field[own.position.row][i];
			
			if(m!=null)
			{
				targ = m;
				break;
			}
		}
		
		field = b.getPlayerField(own.position.color);
		
		//dont debuff, if there is a unit on our side between
		if(targ!=null)
		{
			for(int i=own.position.column-1; i >= 0; i--)
			{
				Minion m = field[own.position.row][i];
			
				if(m!=null)
				{
					targ = null;
					break;
				}
			}
		}
		
		
		if(targ!=null && targ.oumLaseGuardAttackCounter==0)
		{
			targ.oumLaseGuardMoveCounter-=1000;
			targ.oumLaseGuardAttackCounter-=3;
			targ.buffMinion(-3, 0, 0, b);
			targ.moveChanges-=1000;
		}
		
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		
		if(summonedMinion.position.color == triggerEffectMinion.position.color && 
				summonedMinion.position.row == triggerEffectMinion.position.row && 
				summonedMinion.position.column < triggerEffectMinion.position.column && 
				summonedMinion.typeId != 381 )
		{
			//unbuff enemy minion in row:
			Minion[][] field = b.getPlayerField(Board.getOpposingColor(triggerEffectMinion.position.color));
			Minion targ = null;
			for(int i=0; i< 3; i++)
			{
				Minion m = field[triggerEffectMinion.position.row][i];
				
				if(m!=null)
				{
					if(m.oumLaseGuardAttackCounter!=0)
					{
						m.buffMinion(-m.oumLaseGuardAttackCounter, 0, 0, b);
						m.moveChanges+= -m.oumLaseGuardMoveCounter;
						m.oumLaseGuardAttackCounter=0;
						m.oumLaseGuardMoveCounter=0;
					}
				}
			}
			
		}
		
		//on enemy side and same Row is played a new unit
		if(summonedMinion.position.color != triggerEffectMinion.position.color && 
				summonedMinion.position.row == triggerEffectMinion.position.row)
		{
			//unbuff enemy minions in row:
			Minion[][] field = b.getPlayerField(Board.getOpposingColor(triggerEffectMinion.position.color));
			for(int i=0; i< 3; i++)
			{
				Minion m = field[triggerEffectMinion.position.row][i];
				
				if(m!=null)
				{
					//first minion is before played minion in row -> change nothing
					if(summonedMinion.position.column > m.position.column)
					{
						return; //dont change anything
					}
					
					if(m.oumLaseGuardAttackCounter==-3)
					{
						m.buffMinion(3, 0, 0, b);
						m.moveChanges+= 1000;
						m.oumLaseGuardAttackCounter=0;
						m.oumLaseGuardMoveCounter=0;
					}
				}
			}
			
			field = b.getPlayerField(triggerEffectMinion.position.color);
			
			//dont debuff, if there is a unit on our side between
			for(int i=triggerEffectMinion.position.column-1; i >= 0; i--)
			{
				Minion m = field[triggerEffectMinion.position.row][i];
			
				if(m!=null )
				{
					return;
				}
			}
			
			//buff the new minion! (if the new one is not the target -> we returned earlier)
			
			summonedMinion.buffMinion(-3, 0, 0, b);
			summonedMinion.moveChanges-= 1000;
			summonedMinion.oumLaseGuardAttackCounter-=3;
			summonedMinion.oumLaseGuardMoveCounter-=1000;
			
		}
		
		
        return;
    }
	
	
	//TODO could be done more efficient (if we know from where to where the minion moved!) (maybe not at playing rumble)
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		Minion[][] field = b.getPlayerField(triggerEffectMinion.position.color);
		Minion[][] opfield = b.getPlayerField(Board.getOpposingColor(triggerEffectMinion.position.color));
		
		for(int i=0; i<5; i++)
		{
			//only look at the first unit on your side!
			int royalcount = 0; // 1=buff, 2 = unbuff
			for(int j=0; j<3; j++)
			{
				if(field[i][j]!=null)
				{
					if(field[i][j].typeId == 381) 
					{
						//the first minion on that row is oum lasa-> buff (-3 attack + move =0) enemy first unit!
						royalcount=1;
					}
					else
					{
						//first minion on field is not a oum lasa -> unbuff all minions on that side
						royalcount=2;
					}
					break;
				}
			}
			
			//the first minion on that side buff, the others unbuff
			for(int j=0; j<3; j++)
			{
				Minion opp = opfield[i][j];
				if(opp==null) continue;
				
				//buff
				if(royalcount==1 )
				{
					royalcount=2;
					
					if(opp.oumLaseGuardAttackCounter==0)
					{
						opp.buffMinion(-3, 0, 0, b);
						opp.moveChanges-= 1000;
						opp.oumLaseGuardAttackCounter-=3;
						opp.oumLaseGuardMoveCounter-=1000;
					}
					
					continue;
				}
				
				//unbuff
				if(royalcount==2)
				{
					if(opp.oumLaseGuardAttackCounter==-3)
					{
						opp.buffMinion(3, 0, 0, b);
						opp.moveChanges+= 1000;
						opp.oumLaseGuardAttackCounter=0;
						opp.oumLaseGuardMoveCounter=0;
					}
					continue;
				}
				
				
			}
			
		}
    	return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
	
		Minion[][] field = b.getPlayerField(auraendminion.position.color);
		
		//dont debuff, if there is a unit on our side between
		for(int i=auraendminion.position.column-1; i >= 0; i--)
		{
			Minion m = field[auraendminion.position.row][i];
		
			if(m!=null)
			{
				return;//dont need to debuff
			}
		}
		
		//if there is a oum lasa behind dont debuff :D
		for(int i=auraendminion.position.column+1; i < 3; i++)
		{
			Minion m = field[auraendminion.position.row][i];
		
			if(m!=null)
			{ 
				if(m.typeId == 381) return;
				break;
			}
		}
		
		field = b.getPlayerField(Board.getOpposingColor(auraendminion.position.color));
		//unbuff all enemys
		Minion targ = null;
		for(int i=0; i< 3; i++)
		{
			Minion m = field[auraendminion.position.row][i];
			
			if(m!=null)
			{
				if(m.oumLaseGuardAttackCounter!=0)
				{
					m.buffMinion(-m.oumLaseGuardAttackCounter, 0, 0, b);
					m.moveChanges+= -m.oumLaseGuardMoveCounter;
					m.oumLaseGuardAttackCounter=0;
					m.oumLaseGuardMoveCounter=0;
				}
			}
		}
		
        return;
    }
}
