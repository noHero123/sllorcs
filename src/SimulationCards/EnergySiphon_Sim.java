package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class EnergySiphon_Sim extends Simtemplate 
{

	//"id":182,"name":"Energy Siphon","description":"When Energy Siphon comes into play, increase [current] Energy by 3. Enchanted creature gets -1 Attack and -1 Health. "
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, 3);
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(-1, -1, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Energy Siphon", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(1, 1, 0, b);
        return;
    }
	
}
