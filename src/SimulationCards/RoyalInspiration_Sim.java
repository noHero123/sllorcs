package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class RoyalInspiration_Sim extends Simtemplate {
	//"id":359,"name":"Royal Inspiration","description":"Draw 1 Knight scroll."
	//dominion: draw 1 soldier
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		boolean hasdominion = b.isDominionActive(playedCard.position.color);
		
		//draw beast scroll
		UColor col = playedCard.position.color;
		ArrayList<Minion> playerdeck = b.getPlayerDeck(col);
		ArrayList<Minion> hand = b.getPlayerHand(col);
		ArrayList<Minion> deck = new ArrayList<Minion>(playerdeck);
		Boolean found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.getSubTypes().contains(SubType.Knight))
			{
				found=true;
				hand.add(m);
				playerdeck.remove(m);
				b.shuffleList(playerdeck);
				break;
			}
		}
		
		//look into graveyard
		if(!found)
		{
			playerdeck = b.getPlayerGrave(col);
			deck.clear();
			deck.addAll(playerdeck);
			
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.getSubTypes().contains(SubType.Knight))
				{
					found=true;
					hand.add(m);
					playerdeck.remove(m);
					b.shuffleList(playerdeck);
					break;
				}
			}
		}
		
		
		
		if(!hasdominion) 
		{
			if(found)
			{
				//hand+ cardstack update
				b.addMessageToPlayer(col, b.getHandUpdateMessage(col));
				b.addMessageToBothPlayers(b.getCardStackUpdate(col));
			}
			return;
		}
		
		//DRAW SOLDIER
		playerdeck = b.getPlayerDeck(col);
		found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.getSubTypes().contains(SubType.Soldier))
			{
				found=true;
				hand.add(m);
				playerdeck.remove(m);
				b.shuffleList(playerdeck);
				break;
			}
		}
		
		//look into graveyard
		if(!found)
		{
			playerdeck = b.getPlayerGrave(col);
			deck.clear();
			deck.addAll(playerdeck);
			
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.getSubTypes().contains(SubType.Soldier))
				{
					found=true;
					hand.add(m);
					playerdeck.remove(m);
					b.shuffleList(playerdeck);
					break;
				}
			}
		}
		
		if(found)
		{
			//hand+ cardstack update
			b.addMessageToPlayer(col, b.getHandUpdateMessage(col));
			b.addMessageToBothPlayers(b.getCardStackUpdate(col));
		}
		
        return;
    }
	
	public Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {

		/*if(triggerEffectMinion.owner.Ac>=0 && triggerEffectMinion.owner.subtypes.contains(subType.Beast))
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}*/
		
		triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return true;//buff is removed, so we return true
    }
}
