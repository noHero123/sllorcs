package SimulationCards;

import java.awt.Color;
import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class FieldsOfStrife_Sim extends Simtemplate {
	//"id":333,"name":"Fields of Strife","description":"All Knights have their Attack increased by the number of friendly units on the same row."
	//linger 4

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 4;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
		if(!isNew) return;
		int strifes =0;
		for(Minion rules : b.getAllRules())
		{
			if(rules.typeId == 333) strifes++;
		}
		
		this.buffMinionsOnSide(b, UColor.white, strifes);
		this.buffMinionsOnSide(b, UColor.black, strifes);
		
	    return;
	 }
	 
	 private void buffMinionsOnSide(Board b, UColor col, int strifes)
	 {
		 Minion[][] field = b.getPlayerField(col);
		 for(int i=0; i<5; i++)
			{
				int royalcount = 0;//count number of minions
				for(int j=0; j<3; j++)
				{
					if(field[i][j]!=null)
					{
						royalcount+= strifes;
					}
				}
				
				for(int j=0; j<3; j++)
				{
					if(field[i][j]==null) continue;
					
					if(!field[i][j].getSubTypes().contains(SubType.Knight)) continue;
					
					int diff = royalcount - field[i][j].fieldsOfStrifeCounter - 1*strifes; //-1 because the knight himself doesnt count
					
					if(diff > 0)
					{
						//new ducal infantryman was added to row!
						field[i][j].buffMinion(diff, 0, 0, b);
						field[i][j].fieldsOfStrifeCounter+=diff;
					}
					
					if(diff < 0)
					{
						//ducal moved away
						field[i][j].buffMinion(diff, 0, 0, b);
						field[i][j].fieldsOfStrifeCounter+=diff;
					}
					
				}
				
			}
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
		 	int strifes =0;
			for(Minion rules : b.getAllRules())
			{
				if(rules.typeId == 333) strifes++;
			}
		 
			Minion[][] field = b.getPlayerField(summonedMinion.position.color);
			int i = summonedMinion.position.row;
			int royalcount = 1;//1 because a new one is summoned!
			for(int j=0; j<3; j++)
			{
				if(field[i][j]!=null)
				{
					royalcount+=strifes;
				}
			}
			
			for(int j=0; j<3; j++)
			{
				if(field[i][j]==null) continue;
				
				if(!field[i][j].getSubTypes().contains(SubType.Knight)) continue;
				
				int diff = royalcount - field[i][j].fieldsOfStrifeCounter - 1*strifes;//-1 because the knight himself doesnt count
				
				if(diff > 0)
				{
					//new ducal infantryman was added to row!
					field[i][j].buffMinion(diff, 0, 0, b);
					field[i][j].fieldsOfStrifeCounter+=diff;
				}
				
				if(diff < 0)
				{
					//ducal moved away
					field[i][j].buffMinion(diff, 0, 0, b);
					field[i][j].fieldsOfStrifeCounter+=diff;
				}
				
			}
			
			//BUff summoned one:
			if(!field[i][summonedMinion.position.column].getSubTypes().contains(SubType.Knight)) return;
			int diff = royalcount - strifes;
			diff = diff*strifes;
			if(diff > 0)
			{
				//new ducal infantryman was added to row!
				field[i][summonedMinion.position.column].buffMinion(diff, 0, 0, b);
				field[i][summonedMinion.position.column].fieldsOfStrifeCounter+=diff;
			}
			
	        return;
	    }
	 
	 public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
	    {
		 	int strifes =0;
			for(Minion rules : b.getAllRules())
			{
				if(rules.typeId == 333) strifes++;
			}
			this.buffMinionsOnSide(b, UColor.white, strifes);
			this.buffMinionsOnSide(b, UColor.black, strifes);
			
	        return;
	    }
		
		public void onFieldChanged(Board b, Minion triggerEffectMinion)
		{
			int strifes =0;
			for(Minion rules : b.getAllRules())
			{
				if(rules.typeId == 333) strifes++;
			}
			
			this.buffMinionsOnSide(b, UColor.white, strifes);
			this.buffMinionsOnSide(b, UColor.black, strifes);
			
		}


}
