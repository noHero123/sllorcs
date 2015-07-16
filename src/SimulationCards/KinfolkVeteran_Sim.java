package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class KinfolkVeteran_Sim extends Simtemplate {
	//"id":75,"name":"Kinfolk Veteran","description":"","flavor":"It's tough getting old in this business.","subTypesStr":"Human,Kinfolk","subTypes":["Human","Kinfolk"],"kind":"CREATURE","rarity":1,"hp":3,"ap":3,"ac":2,"costDecay":0,"costOrder":0,"costGrowth":5,"costEnergy":0,"set":3,"limitedWeight":0.5,"tags":{"sound_attack_delay":0.25,"sound_attack":"attack_generic_swing_01","sound_attack_pitch_min":1.05,"sound_attack_pitch_max":1.15,"sound_attack_start":"init"},"cardImage":540,"animationPreviewImage":788,"animationPreviewInfo":"113.1,78.1,0.25","animationBundle":476,"abilities":[{"id":"Move","name":"Move","description":"Move unit to adjacent tile","cost":{"DECAY":0,"ORDER":0,"ENERGY":0,"GROWTH":0,"SPECIAL":0}}],"targetArea":"FORWARD","passiveRules":[{"displayName":"Haste","description":"Creature comes in play with Countdown set to 0."}],"available":true

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.buffMinion(0, 0, -1000, b);//haste
        return;
    }
	
}
