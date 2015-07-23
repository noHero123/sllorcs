package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Summons_Sim extends Simtemplate {
	//"id":105,"name":"Summons","description":"Draw 1 structure scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> deck = new ArrayList<Minion>(b.currentDeck);
		Boolean found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.card.cardKind == Kind.STRUCTURE)
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
				if(m.card.cardKind == Kind.STRUCTURE)
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
	
}
