package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class CaravansOfTheExpanse_Sim extends Simtemplate {
	//"id":338,"name":"Caravans of the Expanse","description":"Other [Dominion] traits are active."
	//linger 5

	//IT DOES IGNORE other caravans (of enemy)
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 5;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		
		boolean isNew = b.addRule(playedCard);
		
		if( isNew)
		{
			for(Minion m: b.getAllMinionOfField())
			{//activate dominion
				m.card.cardSim.onDominonOccours(b, m);
			}
			
				playedCard.turnCounter=0;
				for(Minion idol : b.getPlayerIdols(Board.getOpposingColor(playedCard.position.color)))
				{
					if(idol.Hp<=0) playedCard.turnCounter=1;
				}
		 
				if(playedCard.turnCounter==1)
				{
					for(Minion m: b.getPlayerFieldList(playedCard.position.color))
					{
						if(m.getAc()>=0)
						{
							m.buffMinion(1, 0, 0, b);
						}
					}
				}
		}
		
	    return;
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
			if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff own minions
			
			
			
			if(summonedMinion.getAc()>=0)
			{
				summonedMinion.buffMinion(1, 0, 0, b);
			}
	        return;
	    }
		
	 
		public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
	    {
			//unbuff our minions
			for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
			{
				if(m.getAc()>=0)
				{
					m.buffMinion(-1, 0, 0, b);
				}
			}
			
			//remove dominion effects, if there is no dominion anymore
			UColor oppcol = Board.getOpposingColor(auraendminion.position.color);
			//test for your side:
			
			boolean hasdominion =false;
			//maybe our opponent has still caravans?
			for(Minion rule : b.getPlayerRules(oppcol))
			{
				if(rule.typeId == 338) hasdominion = true;
			}
			
			for(Minion mnn : b.getPlayerIdols(oppcol))
			{
				if(mnn.Hp<=0) hasdominion = true;
			}
			
			if(!hasdominion)
			{
				for(Minion mnn : b.getPlayerFieldList(auraendminion.position.color))
				{
					mnn.card.cardSim.onDominonGoesAway(b, mnn);
				}
			}
			
			hasdominion =false;
			//maybe our opponent has still caravans? Still oppcol, because we have no caravans anymore :D
			for(Minion rule : b.getPlayerRules(oppcol))
			{
				if(rule.typeId == 338) hasdominion = true;
			}
			
			for(Minion mnn : b.getPlayerIdols(auraendminion.position.color))
			{
				if(mnn.Hp<=0) hasdominion = true;
			}
			
			if(!hasdominion)
			{
				for(Minion mnn : b.getPlayerFieldList(oppcol))
				{
					mnn.card.cardSim.onDominonGoesAway(b, mnn);
				}
			}
			
			
	        return;
	    }
	 	

	 
		 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
		 {
			 if(diedMinion.isIdol && triggerEffectMinion.turnCounter==0)
			 {
				 triggerEffectMinion.turnCounter=1;
				 for(Minion m : b.getPlayerFieldList(triggerEffectMinion.position.color))
					{
						if(m.getAc()>=0)
						{
							m.buffMinion(1, 0, 0, b);
						}
					}
			 }
			 
		      return;
		 }

}
