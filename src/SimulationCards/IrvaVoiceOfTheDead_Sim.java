package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UPosition;

public class IrvaVoiceOfTheDead_Sim extends Simtemplate {
	//"id":382,"name":"Irva, Voice of the Dead","description":"When an Undead you control deals damage, Irva makes a ranged attack dealing 2 [physical damage]."
	//unique
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 382) b.destroyMinion(m, own);
		}
		
		
    }
	
	
	private Minion getRangedTarget(Board b, Minion m)
	{
		UColor oppcol = Board.getOpposingColor(m.position.color);
		
		for(int i=0;i<3;i++)
		{
			Minion mnn = b.getMinionOnPosition(new UPosition(oppcol, m.position.row, i));
			if(mnn!=null) return mnn;
		}
		
		
		return b.getPlayerIdol(oppcol, m.position.row);
	}
	
	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker.position.color != triggerEffectMinion.position.color || attacker == triggerEffectMinion || dmgdone<=0) return;
		if(attacker.typeId == 382) return; //prevent loops :D
		Minion target = triggerEffectMinion;
		b.doDmg(this.getRangedTarget(b, target), target, 2, AttackType.RANGED, DamageType.PHYSICAL);
		
        return;
    }
	 
}
