package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Monstrosity_Sim extends Simtemplate {
	//"id":247,"name":"Monstrosity","description":"Monstrosity gets +1 Attack for each adjacent Monstrosity, and -1 Attack for other adjacent creatures."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int buff = 0;
		
		for(Minion m : b.getMinionsFromPositions(own.position.getNeightbours()))
		{
			if(m.typeId == 247)
			{
				buff++;
			}
			else
			{
				buff--;
			}
		}
		own.monstroCounter=buff;
		
		if(buff!=0) own.buffMinion(buff, 0, 0, b);
		
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		
		if(summonedMinion.position.isNeightbour(triggerEffectMinion.position))
		{
			if(summonedMinion.typeId == 247)
			{
				triggerEffectMinion.monstroCounter++;
				triggerEffectMinion.buffMinion(1, 0, 0, b);
			}
			else
			{
				triggerEffectMinion.monstroCounter--;
				triggerEffectMinion.buffMinion(-1, 0, 0, b);
			}
		}
        return;
    }
	
	
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		Minion[][] field = b.getPlayerField(triggerEffectMinion.position.color);
		
		int buff = 0;
		
		for(Minion m : b.getMinionsFromPositions(triggerEffectMinion.position.getNeightbours()))
		{
			if(m.typeId == 247)
			{
				buff++;
			}
			else
			{
				buff--;
			}
		}
		
		int diff = buff - triggerEffectMinion.monstroCounter;
		if(diff!=0) triggerEffectMinion.buffMinion(diff, 0, 0, b);
		triggerEffectMinion.monstroCounter=buff;
		
    	return;
    }
}
