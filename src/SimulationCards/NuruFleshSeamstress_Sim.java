package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UPosition;

public class NuruFleshSeamstress_Sim extends Simtemplate {
	//id":278,"name":"Nuru, Flesh-seamstress","description":"Nuru does not attack. When Nuru's Countdown becomes 0, summon 2 <Husks> with Countdown 0 at random positions. The Husks are destroyed after attacking."
	//dominion = buff undead +2 attack
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 278) b.destroyMinion(m, own);
		}
		
		own.turnCounter=0;
		if(b.isDominionActive(own.position.color))
		{
			own.turnCounter=1;
			buffOtherUndead(b.getPlayerFieldList(own.position.color), b);
		}
		
		
    }
	
	 public boolean isRelentless(Board b ,Minion m)
	 {
		 return true;
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
			if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff own minions
			
			if(summonedMinion.position.isEqual(triggerEffectMinion.position) || triggerEffectMinion.owner != null ) return; //dont buff himself, or buff as enchantment!
			
			if(summonedMinion.getSubTypes().contains(SubType.Undead) && triggerEffectMinion.turnCounter==1 ) //only buff new minions, when dominion is active
			{
				summonedMinion.buffMinion(2, 0, 0, b);
			}
	        return;
	    }
	 
	private void buffOtherUndead(ArrayList<Minion> minions, Board b)
	{
		for(Minion m : minions)
		{
			if(m.getSubTypes().contains(SubType.Undead)) m.buffMinion(2, 0, 0, b);
		}
	}
	 
	public void onCountdownReachesZero(Board b , Minion m)
    {
		if(m.owner != null) return;
		
		Card c = CardDB.getInstance().cardId2Card.get(163);
		Minion ill = new Minion(c, -1, m.position.color);
		ArrayList<UPosition> freepos = b.getFreePositions(m.position.color);

		if(freepos.size() ==0) return;
		
		int randint = b.getRandomNumber(0, freepos.size()-1);
		b.summonUnitOnPosition(freepos.get(randint), ill);
		
		
		Minion ill2 = new Minion(c, -1, m.position.color);
		freepos = b.getFreePositions(m.position.color);
		
		if(freepos.size() ==0) 
		{
			ill.turnCounter = 99;//99 for husks-> kill after attacking
			ill.buffMinion(0, 0, -100, b);
			return;
		}
		
		randint = b.getRandomNumber(0, freepos.size()-1);
		b.summonUnitOnPosition(freepos.get(randint), ill2);
		
		ill.turnCounter = 99;
		ill.buffMinion(0, 0, -100, b);
		ill2.turnCounter = 99;
		ill2.buffMinion(0, 0, -100, b);
		
    	return;
    }
	 
	
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner!=null) return;
		 
		 if(diedMinion.isIdol && diedMinion.position.color != triggerEffectMinion.position.color && triggerEffectMinion.turnCounter==0)
		 {
			 triggerEffectMinion.turnCounter=1;
			 //dominion is now active, buff all undead!
			 this.buffOtherUndead(b.getPlayerFieldList(triggerEffectMinion.position.color), b);
		 }
		
		 
	      return;
	 }
	 
	 
	 
	 public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
	    {
		 
		 	if(auraendminion.owner != null || auraendminion.turnCounter ==0) return;
		 	auraendminion.turnCounter=0;
		 	//the real nuru died + dominion was active -> unbuff undead
			for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
			{
				if(m.getSubTypes().contains(SubType.Undead))
				{
					m.buffMinion(-2, 0, 0, b);
				}
			}
	        return;
	    }
	 
	 
	 public void onDominonOccours(Board b , Minion triggerEffectMinion)
	    {

		 if(triggerEffectMinion.turnCounter==1) return;
		 triggerEffectMinion.turnCounter=1;
			for(Minion m : b.getPlayerFieldList(triggerEffectMinion.position.color))
			{
				if(m.getSubTypes().contains(SubType.Undead))
				{
					m.buffMinion(2, 0, 0, b);
				}
			}
			
	    	return;
	    }
	 
	 
	 public void onDominonGoesAway(Board b , Minion triggerEffectMinion)
	    {

		 	if(triggerEffectMinion.turnCounter==0) return;
		 	triggerEffectMinion.turnCounter=0;
			for(Minion m : b.getPlayerFieldList(triggerEffectMinion.position.color))
			{
				if(m.getSubTypes().contains(SubType.Undead))
				{
					m.buffMinion(-2, 0, 0, b);
				}
			}
			
	    	return;
	    }
	 
}
