package SimulationCards;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class WingsCaptain_Sim extends Simtemplate {
	//id":254,"name":"Wings Captain","description":"When Wings Captain's Countdown becomes 0, Wings Captain and adjacent Soldiers have their [Move] increased by 1 until end of turn."
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
    	return;
    }
	

	//TODO buffname?
	public void onCountdownReachesZero(Board b , Minion m)
    {
		m.moveChanges+=1;
		m.addnewEnchantments("BUFF", "Wings Captain", m.card.cardDescription, m.card, b, m.position.color);
		for( Minion mnn : b.getMinionsFromPositions(m.position.getNeightbours()) )
		{
			if(mnn.getAc()>=0 && mnn.getSubTypes().contains(SubType.Soldier))
			{
				mnn.moveChanges+=1;
				mnn.addnewEnchantments("BUFF", "Wings Captain", m.card.cardDescription, m.card, b, m.position.color);
			}
		}
    	return;
    }
	
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	 {
		 if(triggerEffectMinion.owner!=null) return false; // its not the buff :D
		 triggerEffectMinion.moveChanges-=1;
	     return true;
	 }

	
}
