package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class ConcentrateFire_Sim extends Simtemplate
{
	//"id":149,"name":"Concentrate Fire","description":"Target Ranged or Lobber unit makes an extra attack after its next attack."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_lobbers_or_ranged_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Concentrate Fire", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		//do annother attack!
		if(m != self.owner)
        {
			return;
        }
		//remove enchantment
		ArrayList<Minion> temp = new ArrayList<Minion>(m.attachedCards);
		for(Minion e : temp)
		{
			if(e == self) 
			{
				m.attachedCards.remove(e);
				break;
			}
		}
		b.addMinionToGrave(self);
		b.addMessageToBothPlayers(b.getStatusUpdateMessage(m));
		
		//minion attacks a second time
		Color otherColor = Board.getOpposingColor(m.position.color);
		Minion[][] defffield = b.getPlayerField(otherColor);
		b.unitAttacking(m, defffield, m.Ap, m.attackType, DamageType.COMBAT);
		
    	return;
    }
	
}
