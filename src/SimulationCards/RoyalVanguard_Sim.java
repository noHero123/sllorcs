package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.subType;

public class RoyalVanguard_Sim extends Simtemplate {
	//"id":54,"name":"Royal Vanguard","description":"When Royal Vanguard's Countdown becomes 0, adjacent units get +2 Attack until end of turn."
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
    	return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	

	public void onCountdownReachesZero(Board b , Minion m)
    {
		for( Minion mnn : b.getMinionsFromPositions(m.position.getNeightbours()) )
		{
			if(m.getAc()>=0)
				{
				mnn.buffMinionWithoutMessage(2, 0, 0, b);
				mnn.addnewEnchantments("BUFF", "Royal Vanguard", m.card.cardDescription, m.card, b, m.position.color); //TODO its name is not royal vanguard isnt it?
				}
			}
    	return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		
		//if()
		if(triggerEffectMinion.owner==null) return false; //we need the buff
		
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}
		
		
        return true;//buff is removed, so we return true
    }
	
	
	
}
