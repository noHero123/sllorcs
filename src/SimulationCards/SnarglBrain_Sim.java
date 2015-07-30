package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class SnarglBrain_Sim extends Simtemplate 
{

	//"id":369,"name":"Snargl Brain","description":"Enchanted unit gets +1 Attack. If Energy is 4 or more, enchanted unit gets +3 Health. "
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff=0;
		if(b.getMaxRessource(ResourceName.ENERGY, target.position.color) >= 4) buff = 3;
		
		target.buffMinionWithoutMessage(1, buff, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Snargl Brain", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
    public  void onEnergyChanged(Board b, Minion triggerEffectMinion, UColor player, ResourceName ressi)
    {
    	if(triggerEffectMinion.owner.position.color == player && ressi == ResourceName.ENERGY && b.getMaxRessource(ResourceName.ENERGY, triggerEffectMinion.owner.position.color) == 4)
    	{
    		triggerEffectMinion.owner.buffMinionWithoutMessage(0, 3, 0, b);
    	}
        return;
    }
    
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	int buff=0;
	 	if(b.getMaxRessource(ResourceName.ENERGY, m.position.color) >= 4) buff = 3;
	 	m.owner.buffMinionWithoutMessage(-1, -buff, 0, b);
        return;
    }
	
}
