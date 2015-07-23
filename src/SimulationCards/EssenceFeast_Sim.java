package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class EssenceFeast_Sim extends Simtemplate {
	//"id":56,"name":"Essence Feast","description":"Idols you control are healed by 1. Beasts get +2 Attack until end of turn. Draw 1 Beast."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerIdols(playedCard.position.color))
		{
			m.healMinion(1, b);
		}
		
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.getSubTypes().contains(SubType.Beast))
			{
				m.buffMinionWithoutMessage(2, 0, 0, b);
				m.addnewEnchantments("BUFF", "Essence Feast", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
		
		//draw creature scroll
		ArrayList<Minion> deck = new ArrayList<Minion>(b.currentDeck);
		Boolean found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.getSubTypes().contains(SubType.Beast))
			{
				found=true;
				b.currentHand.add(m);
				deck.remove(i);
				b.shuffleList(deck);
				break;
			}
		}
		
		//look into graveyard
		if(!found)
		{
			deck.clear();
			deck.addAll(b.currentGrave);
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.getSubTypes().contains(SubType.Beast))
				{	
					found=true;
					b.currentHand.add(m);
					deck.remove(i);
					b.shuffleList(deck);
					break;
				}
			}
		}
		
		if(found)
		{
			//hand+ cardstack update
			b.addMessageToPlayer(b.activePlayerColor, b.getHandUpdateMessage(b.activePlayerColor));
			b.addMessageToBothPlayers(b.getCardStackUpdate(b.activePlayerColor));
		}
		
        return;
    }
	
	public Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {

		/*if(triggerEffectMinion.owner.Ac>=0 && triggerEffectMinion.owner.subtypes.contains(subType.Beast))
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}*/
		
		triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return true;//buff is removed, so we return true
    }
}
