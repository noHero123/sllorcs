package SimulationCards;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class WoodlandMemorial_Sim extends Simtemplate {
	//"id":111,"name":"Woodland Memorial","description":"When you play a spell or enchantment, Woodland Memorial is destroyed. Increase Growth by 1."
	
	public void onPlayerPlayASpellOrEnchantment(Board b ,Minion triggerEffectMinion, Minion spell)
    {
		if(triggerEffectMinion.position.color != spell.position.color)  return;
		
		b.changeCurrentRessource(ResourceName.GROWTH, triggerEffectMinion.position.color, 1);
		
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
        return;
    }
	
	
}
