package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class UnleashInnerPower_Sim extends Simtemplate 
{

	//"id":58,"name":"Unleash Inner Power","description":"Target creature's Attack is increased by its Health value. Its Health is then set to 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int hp = target.Hp;
		target.buffMinionWithoutMessage(hp, 0, 0, b);//status update is done in add card as enchantment
		target.Hp = 2; 
		target.maxHP = 2;
		target.addCardAsEnchantment("ENCHANTMENT", "Unleash Inner Power", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}
