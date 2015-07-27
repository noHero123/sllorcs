package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class CrownOfStrength_Sim extends Simtemplate {
	//"id":15,"name":"Crown of Strength","description":"Enchanted unit gets +1 Attack and +2 Health, and counts as a Knight."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	//TODO are the other subtypes are deleted?
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));

		target.buffMinionWithoutMessage(1, 2, 0, b);

		target.addSubtype(SubType.Knight, b);
		target.addCardAsEnchantment("ENCHANTMENT", "Crown of Strength", playedCard.card.cardDescription, playedCard, b);
		target.addnewEnchantments("STARTBUFF", "Knight", "This unit has the Knight subtype.", playedCard.card, b, playedCard.position.color);
        return;
    }
	
	// TODO we do a special onDeathrattle only for enchantments in addMinionToGrave!
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		//remove attack of others metelhearted minions
		m.removeSubtype(SubType.Knight, b);
        return;
    }
	
}
