package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class WoodlandMemorial_Sim extends Simtemplate {
	//"id":111,"name":"Woodland Memorial","description":"When you play a spell or enchantment, Woodland Memorial is destroyed. Increase Growth by 1."
	
	public void onPlayerPlayASpellOrEnchantment(Board b ,Minion triggerEffectMinion, Minion spell)
    {
		if(triggerEffectMinion.position.color != spell.position.color)  return;
		
		int[] curE = b.whiteRessources;
		if(triggerEffectMinion.position.color == Color.black) curE = b.blackRessources;
		
		curE[1] +=1;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
        return;
    }
	
	
}
