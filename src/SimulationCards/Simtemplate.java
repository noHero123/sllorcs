package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Simtemplate {
	//#############
	//the basis class for all simulation-stuff
	
	

	//only used for Spells and enchantments... units + structures automatically select all free tiles of your side
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	//only few cards uses a second target (like transposition) 
	//if none is replaced, there is no second selection in this case we just return "tileSets":[[ ... ]]
	//if something other then none is returned, we return "tileSets":[[...],[...]]
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.None;
	}
	
	//spell is played
    public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
        return;
    }

    //minion is played on field
    public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
        return;
    }
    
    
    
    //a new Minion is summoned and got the buff this minion (which is allready on board) provides
    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
        return;
    }

    //NOT CALLED on Metamorphosis
    //TODO call this if minion died, or replayed to hand, or destroyed (same as died?)
    public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
        return;
    }

    //return true for special attack like ether pump + charge coil
    public  boolean hasSpecialAttack()
    {
        return false;
    }
    
  //minion is doing special attack
    public  void doSpecialAttack(Board b, Minion own)
    {
        return;
    }
    
    //return false if unit doesnt attack, (like automaton)
    public  boolean doesAttack(Board b, Minion m)
    {
        return true;
    }
    
    
    //for: hellspliter mortal target (random tile)
    public boolean hasSpecialAttackTarget()
    {
    	return false;
    }
    
    //for hellspliter choose random tile here!
    public ArrayList<Position> getSpecialAttackTarget(Board b, Minion m)
    {
    	ArrayList<Position> posis = new ArrayList<Position>();
    	
    	return posis;
    }

    //for stuff like clock library
    //TODO implement this trigger
    public void onCountdownReachesZero(Board b , Minion m)
    {
    	return;
    }
    
    //for effects like aging knight + Automaton forge + the other forge :D are done this way
    public void onAttackDone(Board b , Minion m, Minion self)
    {
    	
    	return;
    }
    

    // returns the spiky damage for the unit or the enchantment that is providing the dmg or the linger-spell
    public int getSpikyDamage(Board b ,Minion m)
    {
    	return 0;
    }
    
    // returns if unit or the enchantment is poisonous
    public boolean isPoisonous(Board b ,Minion m)
    {
    	return false;
    }
    
 // returns if unit or the enchantment is poisonous
    public boolean isRelentless(Board b ,Minion m)
    {
    	return false;
    }
    
    public boolean reduceDmgToOne(Board b ,Minion m)
    {
    	return false;
    }
    
    //unit takes dmg trigger (like bloodboil)
    // IS ALSO CALLED IF DMG WAS 0!
    public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg)
    {
        return;
    }
    
    //unit did dmg trigger (like arthritis)
    public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion)
    {
        return;
    }
    
    //for effects like elan vital or other heal effects
    public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
    {
        return;
    }
    
  //for effects like that order soldier that triggers if a enemy unit died in combat or buffs that lasts till end of turn
    //last parameter could be null if its not a attached card
    //returns true if it is deleted (current used in minion turnEndingDebuffing)
    //triggerEffectMinion = minion that triggers the effect himself
    
    //maybe another function for enchantments? (cause kinfolk has a enchantment, but is a minion so his enchantment remove is called when he dies :D)
    public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
        return false;
    }
    
    //minion died (only itself)
    public  void onDeathrattle(Board b, Minion m)
    {
        return;
    }
    
    // a minion died (not itself)

    public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion)
    {
        return;
    }
    
    //the field changed (like unit moved, died, new summoned)
    public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
    	return;
    }
    
    //ability is activated
    public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
        return;
    }
    
    //like ember bonds
    public  void onUnitIsGoingToAttack(Board b, Minion triggerEffectMinion, Minion attacker )
    {
        return;
    }
    
    
    
    
    //not yet used!
    
    public  void onAMinionGotHealedTrigger(Board p, Minion triggerEffectMinion, boolean ownerOfMinionGotHealed)
    {
        return;
    }

    public  void onAHeroGotHealedTrigger(Board p, Minion triggerEffectMinion, boolean ownerOfHeroGotHealed)
    {
        return;
    }

    


    

    public  void onMinionWasSummoned(Board p, Minion triggerEffectMinion, Minion summonedMinion)
    {
        return;
    }

    

    public  void onCardIsGoingToBePlayed(Board p, Card c, boolean wasOwnCard, Minion triggerEffectMinion)
    {
        return;
    }

    public  void onCardWasPlayed(Board p, Card c, boolean wasOwnCard, Minion triggerEffectMinion)
    {
        return;
    }
    
    
    public static Simtemplate intToSimtemplate(int id)
    {
    	//energy set 1 and 2 + etherpump 
    	if(id == 24) return new CatapultOfGoo_Sim();
    	if(id == 27) return new Burn_Sim();
    	if(id == 35) return new InfernoBlast_Sim();
    	if(id == 39) return new IronOgre_Sim();
    	if(id == 47) return new PotionOfResistance_Sim();
    	if(id == 66) return new CopperAutomaton_Sim();
    	if(id == 68) return new CannonAutomaton_Sim();
    	if(id == 79) return new MachinePriest_Sim();
    	if(id == 80) return new EmberBonds_Sim();
    	if(id == 83) return new GolemSkin_Sim();
    	if(id == 84) return new EtherPump_Sim();
    	if(id == 85) return new MagmaPack_Sim();
    	
    	if(id == 96) return new GunAutomaton_Sim();
    	if(id == 98) return new UselessContraption_Sim();
    	if(id == 108) return new LawMemorial_Sim();
    	if(id == 110) return new TribalMemorial_Sim();
    	if(id == 118) return new HellspitterMortar_Sim();
    	if(id == 140) return new Overdrive_Sim();
    	if(id == 141) return new Plating_Sim();
    	if(id == 142) return new IronWhip_Sim();
    	if(id == 143) return new Incendiaries_Sim();
    	if(id == 147) return new BlindRage_Sim();
    	if(id == 148) return new MachinationMindset_Sim();
    	if(id == 149) return new ConcentrateFire_Sim();
    	if(id == 150) return new ToolInitiate_Sim();
    	if(id == 151) return new MetalHeart_Sim();
    	if(id == 152) return new Bombard_Sim();
    	
    	
    	//growth set 1 and 2:
    	if(id == 1) return new GravelockElder_Sim();
    	if(id == 4) return new Gravehawk_Sim();
    	if(id == 13) return new ElanVital_Sim();
    	if(id == 16) return new BearPaw_Sim();
    	if(id == 17) return new BindingRoot_Sim();
    	if(id == 18) return new Hymn_Sim();
    	if(id == 21) return new Rallying_Sim();
    	if(id == 22) return new BrotherOfTheWolf_Sim();
    	if(id == 26) return new ChampionRing_Sim();
    	if(id == 29) return new FertileSoil_Sim();
    	if(id == 34) return new Quake_Sim();
    	if(id == 38) return new GreatWolf_Sim();
    	if(id == 40) return new Junkyard_Sim();
    	if(id == 41) return new KinfolkBrave_Sim();
    	if(id == 42) return new KinfolkJarl_Sim();
    	if(id == 43) return new LeechingRing_Sim();
    	if(id == 44) return new MangyWolf_Sim();
    	if(id == 45) return new DryadicPower_Sim();
    	if(id == 49) return new RaggedWolf_Sim();
    	if(id == 53) return new SisterOfTheFox_Sim();
    	if(id == 56) return new EssenceFeast_Sim();
    	if(id == 60) return new EternalStatue_Sim();
    	if(id == 63) return new VitalityWell_Sim();
    	if(id == 65) return new AncestralTotem_Sim();
    	if(id == 75) return new KinfolkVeteran_Sim();
    	if(id == 76) return new Rumble_Sim();
    	if(id == 78) return new CrimsonBull_Sim();
    	if(id == 84) return new FrostGale_Sim();
    	if(id == 88) return new VitriolAura_Sim();
    	if(id == 89) return new EyeOfEagle_Sim();
    	if(id == 91) return new Frostbeard_Sim();
    	if(id == 94) return new Nutrition_Sim();
    	if(id == 100) return new RangersBane_Sim();
    	if(id == 112) return new SandPactMemorial_Sim();
    	if(id == 113) return new StonePactMemorial_Sim();
    	if(id == 114) return new IllthornSeed_Sim();
    	if(id == 115) return new Illthorn_Sim();
    	if(id == 129) return new Bunny_Sim();
    	if(id == 154) return new Wildling_Sim();
    	if(id == 156) return new VaettrOfTheWild_Sim();
    	if(id == 189) return new BeastRat_Sim();
    	
    	return new Simtemplate();
    }
}
