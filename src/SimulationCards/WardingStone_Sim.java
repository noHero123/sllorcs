package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class WardingStone_Sim extends Simtemplate {
	//"id":315,"name":"Warding Stone","description":"When Warding Stone is destroyed in combat, attacking unit is returned to owner's hand and you draw 1 scroll."
	//TESTED: crone kills only, if the killing dmg is from an unit! 
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		if(b.gameState != GameState.Battle) return;// NOTE: gamestate = 1 -> we are in battle-phase

		if(attacker.position.color == m.position.color) return;
		if(attacker.cardType == Kind.CREATURE || attacker.cardType == Kind.STRUCTURE)
		{
			b.removeMinionToHand(attacker);
			b.drawCards(m.position.color, 1);
		}
			
		
        return;
    }
	
	
}
