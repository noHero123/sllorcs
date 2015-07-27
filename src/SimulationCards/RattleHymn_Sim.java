package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.SubType;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.TargetAreaGroup;
import BattleStuff.tileSelector;

public class RattleHymn_Sim extends Simtemplate
{
	
	//"id":362,"name":"Rattle Hymn","description":"Destroy a random [Lingering] spell your opponent controls. Draw 1 Lingering spell."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		ArrayList<Minion> rules = b.getPlayerRules(Board.getOpposingColor(playedCard.position.color));
		
		if(rules.size()>=1)
		{
			int random = b.getRandomNumber(0, rules.size()-1);
			Minion rule = rules.get(random);
			b.ruleCountDown(rule, rule.lingerDuration+1); //just to be sure it is destroyed :D
		}
		
		//draw linger spell
		
				UColor col = playedCard.position.color;
				ArrayList<Minion> playerdeck = b.getPlayerDeck(col);
				ArrayList<Minion> hand = b.getPlayerHand(col);
				ArrayList<Minion> deck = new ArrayList<Minion>(playerdeck);
				Boolean found = false;
				for(int i=0; i<deck.size(); i++)
				{
					Minion m= deck.get(i);
					if(m.getSubTypes().contains(SubType.Lingering))
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
						if(m.getSubTypes().contains(SubType.Lingering))
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
	
}
