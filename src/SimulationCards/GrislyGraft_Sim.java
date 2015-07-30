package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class GrislyGraft_Sim extends Simtemplate 
{

	//"id":175,"name":"Grisly Graft","description":"Enchant creature you control. When Grisly Graft comes into play, sacrifice adjacent creatures. Enchanted creature gains Attack and Health equal to the number of creatures sacrificed."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff = 0;
		
		for(Minion m : b.getMinionsFromPositions(target.position.getNeightbours()))
		{
			b.destroyMinion(m, playedCard);
			buff++;
		}
		
		target.buffMinionWithoutMessage(buff, buff, 0, b);//status update is done in add card as enchantment
		playedCard.turnCounter = buff;
		target.addCardAsEnchantment("ENCHANTMENT", "Grisly Graft", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-m.turnCounter, -m.turnCounter, 0, b);
	 	m.turnCounter = 0;
        return;
    }
	
}
