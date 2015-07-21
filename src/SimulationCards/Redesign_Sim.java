package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Redesign_Sim extends Simtemplate 
{

	//"id":145,"name":"Redesign","description":"Target creature's Attack and Health switch values."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int attack = target.Ap;
		int hp = target.Hp;
		int abuff = hp-attack;
		int hbuff = attack-hp;
		target.buffMinionWithoutMessage(abuff, hbuff, 0, b);
		
		target.addCardAsEnchantment("ENCHANTMENT", "Redisign", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}
