package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.BuffType;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class VildaTheVerdant_Sim extends Simtemplate {
	//"id":370,"name":"Vilda the Verdant","description":"Vilda has +1 Attack for each enchantment on your side."
	//unique + ward

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 370) b.destroyMinion(m, own);
		}
		
		int enches = 0;
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			for(Minion ench : m.getAttachedCards())
			{
				if(ench.bufftype == BuffType.ENCHANTMENT) enches++;
			}
		}
		if(enches>=1) own.buffMinion(enches, 0, 0, b);
		
    }
	
	public boolean hasWard(Board b ,Minion m)
    {
    	return true;
    }

	public  void onUnitGotEnchantment(Board b, Minion triggerEffectMinion, Minion minion, boolean isItsFirstEnchantment )
    {
		if(triggerEffectMinion.position.color != minion.position.color) return;
		triggerEffectMinion.buffMinion(1, 0, 0, b);
        return;
    }
    
	
	public  void onUnitLoseEnchantment(Board b, Minion triggerEffectMinion, Minion minion, boolean isLastOne)
    {
		if(triggerEffectMinion.position.color != minion.position.color) return;
		triggerEffectMinion.buffMinion(-1, 0, 0, b);
        return;

    }
	
}
