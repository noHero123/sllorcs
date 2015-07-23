package BattleStuff;

import java.util.ArrayList;

import SimulationCards.Simtemplate;

public class Card {
	//basic stuff of card
	
	//{\"id\":1,\"name\":\"Gravelock Elder\",\"description\":\"Other Gravelocks you control have +1 Health.\",
    //\"flavor\":\"Gravelocks look up to their elders... literally.\",\"subTypesStr\":\"Gravelock\",\
	//"subTypes\":[\"Gravelock\"],
    //\"kind\":\"CREATURE\",\"rarity\":2,\"hp\":5,\"ap\":4,\"ac\":2,\"costDecay\":0,\"costOrder\":0,\"costGrowth\":0,\"costEnergy\":5,\
    //"set\":3,\"limitedWeight\":1.0,\"tags\":{\"sound_attack\":\"impact_gravelock_physical\"},
    //\"cardImage\":479,\"animationPreviewImage\":445,\"animationPreviewInfo\":\"54.75,50.5,0.25\",\"animationBundle\":448,\"
    //abilities\":[{\"id\":\"Move\",\"name\":\"Move\",\"description\":\"Move unit to adjacent tile\",\"cost\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0}}],
    //\"targetArea\":\"FORWARD\",\"
    //passiveRules\":[{\"displayName\":\"Ranged attack\",\"description\":\"This unit is unaffected by effects that apply only to melee units (for example Spiky).\"}]
    //,\"available\":true}
	
	public int typeId = 0;
	public String cardname ="unknown";
	public String cardDescription = "";
	public ArrayList<SubType> subtypes = new ArrayList<SubType>();
	public Kind cardKind = Kind.NONE;
	public int rarity = 0;
	public int hp=0;
	public int ap=0;
	public int ac=-1;
	public int costDecay=0;
	public int costOrder=0;
	public int costGrowth=0;
	public int costEnergy=0;
	public ArrayList<ActiveAbility> abilitys = new ArrayList<ActiveAbility>();
	public targetArea trgtArea = targetArea.UNDEFINED;
	public String trgtAreaString = "UNDEFINED";
	public ArrayList<String> passiveAbilitys = new ArrayList<String>();
	public Simtemplate cardSim = new Simtemplate();
	
	
	public AttackType getAttackType()
	{
		for(String s : this.passiveAbilitys)
		{
			if(s.equals("Ranged attack")) return AttackType.RANGED;
			if(s.startsWith("Lobber")) return AttackType.BALLISTIC;
		}
		return AttackType.MELEE;
	}
	
	
}
