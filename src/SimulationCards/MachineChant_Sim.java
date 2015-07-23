package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class MachineChant_Sim extends Simtemplate
{
	
	//"id":226,"name":"Machine Chant","description":"Deal [magic damage] to target unit equal to the number of structures you control. Increase Energy by 1."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int dmg =0;
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.cardType == Kind.STRUCTURE) dmg++;
		}
		b.doDmg(target, playedCard, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
		
		b.changeMaxRessource(ResourceName.ENERGY, playedCard.position.color, 1);
        return;
    }
	
}
