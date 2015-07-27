package BattleStuff;

import java.util.ArrayList;
import java.util.Iterator;

public class Minion {

	public Card card= new Card();
	public Kind cardType = Kind.NONE;
	public long cardID = -1;
	public int typeId = 0;
	private ArrayList<SubType> subtypes = new ArrayList<SubType>();
	
	private int Ap = 0;
	public int Hp =0;
	private int Ac =-1;//please dont change this :D
	public int maxHP=0;
	public int maxAc=0;
	
	public int movesThisTurn = 0;
	public int basicMove = 1;
	public int moveChanges=0;
	public int tempMove=0;
	
	//public boolean hasPiercing = false; // it doesnt have to be on card, can also be enchantment!
	
	public AttackType attackType = AttackType.MELEE;
	//public int armor = 0;
	public int curse = 0;
	//public int spiky = 0;
	public boolean isPoisonous = false;
	//private int magicRessi=0;
	
	public boolean isToken = true;
	
	//TODO save in position variable
	public UPosition position = new UPosition(UColor.white, -1, -1);
	//public Color color = Color.white;
	//public int row = -1; // 0-4
	//public int column = -1;// 0-2
	public int lvl=0;
	
	public String bufftype ="";
	public String buffDescription ="";
	public String buffName ="";
	
	public int lingerDuration=-1;
	
	private ArrayList<Minion> attachedCards = new ArrayList<Minion>();
	public Minion owner=null; //the unit/structure the enchantment where the enchantment is attached
	
	public boolean isIdol = false;
	
	//temp variables
	public int numberOfDmgTaken=0;
	public boolean deadTriggersDone=false;
	public int currentAttackPlus = 0; // for kinfolk jarl 
	public int turnsInplay=0;
	public int aoeDmgToDo=0;
	public int desperationBuffs=0;
	public boolean imuneToNextDmg=false;
	public boolean imuneToDmg=false;
	public boolean addToHandAfterDead = false;
	
	//TODO calculate attack/HP life? like getAP() -> loop through enchantments/buffs other minions and calculate the current attack?
	public int frostbeardCounter=0;
	public int ducalInfCounter=0;
	public int royalInfCounter=0;
	public int pillarCounter=0;
	public int monstroCounter =0;
	public int cullingCounter =0;
	public boolean didDmgToIdol=false;
	public boolean fangbear=false;
	public int oumLaseGuardAttackCounter=0;
	public int oumLaseGuardMoveCounter=0;
	
	//its used as a used variable for all minions... can only be changed by itself!
	public int turnCounter = 0; //TODO replace that other counters with it! (not all are possible)
	//TODO use these for entchantments!
	public int attackbuff = 0;
	public int healthbuff = 0;
	
	public int getAc()
	{
		return this.Ac;
	}
	
	public void setAttack(int a)
	{
		this.Ap = a;
	}
	
	public int getAttack(Board b)
	{
		int attack = this.Ap;
		if(this.typeId == 299) attack += b.getCurrentRessource(ResourceName.ENERGY, this.position.color);
		
		for(Minion m : this.attachedCards)
		{
			if(m.typeId == 198) return 0;
		}
		return Math.max(0, attack);
	}
	
	public void resetAc()
	{
		this.Ac = this.maxAc;
	}
	
	public void resetAcWithMessage(Board b)
	{
		int oldAc = this.Ac;
		this.Ac = this.maxAc;
		
		if(oldAc != this.Ac )
		{
			
			b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
		}
	}
	
	public void setAc(int v)
	{
		this.Ac = v;
		this.maxAc=v;
	}
	
	public Minion(Card c, long cid, UColor playercol)
	{
		this.card = c;
		this.cardID = cid;
		this.Ap = c.ap;
		this.Hp = c.hp;
		this.Ac = c.ac;
		this.maxAc = this.Ac;
		this.maxHP = this.Hp;
		this.isToken =false;
		if(cid<=-1) this.isToken=true;
		this.position.color = playercol;
		//this.color = playercol;
		
		this.cardType = c.cardKind;
		this.typeId = c.typeId;
		this.buffName="";
		this.buffDescription="";
		this.bufftype="";
		this.attackType = c.getAttackType();
		this.subtypes.clear();
		for(SubType s : c.subtypes)
		{
			this.subtypes.add(s);
		}
		frostbeardCounter=0;
		ducalInfCounter=0;
		royalInfCounter=0;
		monstroCounter =0;
		this.aoeDmgToDo=0;
		didDmgToIdol=false;
		fangbear=false;
		imuneToDmg=false;
		
		oumLaseGuardAttackCounter=0;
		oumLaseGuardMoveCounter=0;
	}
	
	public Minion getMinionToken()
	{
		Minion temp = new Minion(this.card, -1, this.position.color);

		temp.isToken =true;
		
		return temp;
	}
	
	
	public Minion(int maxHP, UColor col, int pos)
	{
		this.Hp = maxHP;
		this.maxHP = maxHP;
		this.isToken =false;
		this.position.color = col;
		this.isIdol=true;
		this.position.row = pos;
		this.position.column = 4;
		this.aoeDmgToDo=0;
		didDmgToIdol=false;
		fangbear=false;
		imuneToDmg=false;
	}
	
	public Minion (String type, String name, String description, Card c, UColor ownercolor)
	{
		this.bufftype = type;
		this.buffName = name;
		this.buffDescription = description;
		this.card = c;
		this.typeId = c.typeId;
		this.position.color = ownercolor;
		this.isToken=true;
		this.aoeDmgToDo=0;
		didDmgToIdol=false;
		fangbear=false;
		imuneToDmg=false;
	}
	
	/*public String getStatusUpdate()
	{
		String col = "black";
		if(this.color == Color.white) 
			{col = "white";}
		String s = "{\"StatsUpdate\":{\"target\":{\"color\":\"" +col+ "\",\"position\":\""+ this.row +"," + this.column +"\"},\"hp\":"+this.Hp+",\"ap\":"+this.Ap+",\"ac\":"+this.Ac+"}}";
		
		return s;
	}*/
	
	public void setDefaultValues(Board b)
	{
		//this.isRelentless = this.card.cardSim.isRelentless(b, this);
	}
	
	public void reset()
	{
		this.Ap = this.card.ap;
		this.Hp = this.card.hp;
		this.Ac = this.card.ac;
		this.maxAc = this.Ac;
		this.maxHP = this.Hp;
		this.isToken =false;
		if(this.cardID<=-1) this.isToken=true;
		//this.color = playercol;
	
		this.buffName="";
		this.buffDescription="";
		this.bufftype="";
		this.attackType = this.card.getAttackType();
		this.subtypes.clear();
		for(SubType s : this.card.subtypes)
		{
			this.subtypes.add(s);
		}
		this.moveChanges=0;
		//this.isRelentless=false;
		
		numberOfDmgTaken=0;
		deadTriggersDone=false;
		
		currentAttackPlus = 0; // for kinfolk jarl 
		turnsInplay=0;
		aoeDmgToDo=0;
		desperationBuffs=0;
		imuneToNextDmg=false;
		addToHandAfterDead = false;
		this.attachedCards.clear();
		this.owner = null;
		
		frostbeardCounter=0;
		ducalInfCounter=0;
		royalInfCounter=0;
		monstroCounter =0;
		didDmgToIdol=false;
		fangbear=false;
		imuneToDmg=false;
		oumLaseGuardAttackCounter=0;
		oumLaseGuardMoveCounter=0;
		
	}
	
	
	public boolean canMove()
	{
		/*Boolean containss=false;
		for(ActiveAbility aa : this.card.abilitys)
		{
			if(aa.id.equals(activeAbilitys.Move) || aa.id.equals(activeAbilitys.Flying))
			{
				containss=true;
			}
		}*/
		return this.movesThisTurn < this.basicMove + this.tempMove + this.moveChanges;
	}
	
	public boolean isRelentless(Board b)
	{
		boolean rel =  this.card.cardSim.isRelentless(b, this);
		
		for(Minion e: this.attachedCards)
		{
			rel = rel || e.card.cardSim.isRelentless(b, e);
		}
		
		return rel;
	}
	
	public boolean hasWard(Board b)
	{
		boolean rel =  this.card.cardSim.hasWard(b, this);
		
		for(Minion e: this.attachedCards)
		{
			rel = rel || e.card.cardSim.hasWard(b, e);
		}
		if(rel) return true;
		//check if a wings warder is on same row
		Minion[][] field = b.getPlayerField(this.position.color);
		
		for(int i=0; i< 3; i++)
		{
			//it gets ward from wings warder!
			if(this.position.column != i && field[this.position.row][i] != null && field[this.position.row][i].typeId == 298)
			{
				return true;
			}
		}
		
		//check if sancturary of the lost is buffing this minion
		if(this.subtypes.contains(SubType.Undead) && this.cardType == Kind.CREATURE)
		{
			for(Minion rule : b.getAllRules())
			{
				
				if(rule.typeId == 342)
				return true;
			}
		}
		
		return rel;
	}
	
	
	public int getArmor(Board b)
	{
		int ret = 0;
		ret += this.card.cardSim.getArmor(b ,this, this);
		for(Minion e: this.attachedCards)
		{
			ret += e.card.cardSim.getArmor(b ,e, this);
		}
		//There are currently no armor giving lingers
		
		return ret;
	}
	
	public int getMagicRessi(Board b)
	{
		int ret = 0;
		ret += this.card.cardSim.getMagicResistance(b ,this, this);
		for(Minion e: this.attachedCards)
		{
			ret += e.card.cardSim.getMagicResistance(b ,e, this);
		}
		
		if(this.subtypes.contains(SubType.Undead) && this.cardType == Kind.CREATURE)
		{
			for(Minion rule : b.getAllRules())
			{
				
				if(rule.typeId == 342) ret += rule.card.cardSim.getMagicResistance(b ,rule, this);
			}
		}
		return ret;
	}
	
	public boolean hasPiercing(Board b)
	{
		boolean rel =  this.card.cardSim.hasPiercing(b, this);
		
		for(Minion e: this.attachedCards)
		{
			rel = rel || e.card.cardSim.hasPiercing(b, e);
		}
		
		return rel;
	}
	
	
	public boolean hasEnchantment()
	{
		for(Minion e: this.attachedCards)
		{
			if(e.owner!=null) return true;
		}
		
		return false;
	}
	
	public ArrayList<Minion> getTargets(Minion[][] enemyField, ArrayList<UPosition> posis, ArrayList<Minion> idols, Board b)
	{
		ArrayList<Minion> targets = new ArrayList<Minion>();
		if(this.card.trgtArea == targetArea.FORWARD)
		{
			if(this.hasPiercing(b)) 
			{
				int currentattack = this.Ap;
				for(UPosition posi : posis)
				{
					if(posi.row>=0 && posi.row <=4 && posi.column>=0 && posi.column <=2)
					{
						Minion m = enemyField[posi.row][posi.column];
						if(m!=null && m.Hp>=1)
						{
							targets.add(m);
							currentattack = currentattack / 2;
							if(currentattack == 0) return targets;
						}
					}
				}
				
				targets.add( idols.get(this.position.row) );
				return targets;
			}
			
			
			boolean hasSlithering = false;
			for(Minion ench : this.attachedCards)
			{
				if(ench.typeId == 380) hasSlithering=true;
			}
			
			for(UPosition posi : posis)
			{
				if(posi.row>=0 && posi.row <=4 && posi.column>=0 && posi.column <=2)
				{
					Minion m = enemyField[posi.row][posi.column];
					if(hasSlithering)
					{
						if(m!=null && m.Hp>=1 && m.cardType != Kind.STRUCTURE)
						{
							targets.add(m);
							return targets;
						}
					}
					else
					{
						if(m!=null && m.Hp>=1)
						{
							targets.add(m);
							return targets;
						}	
					}
				}
			}
			
			//no enemy on line -> attack idol
			targets.add( idols.get(this.position.row) );
			
			return targets;
		}
		
		if(this.card.trgtArea == targetArea.RADIUS_4)
		{
			
			for(UPosition posi : posis)
			{
				if(posi.row>=0 && posi.row <=4 && posi.column>=0 && posi.column <=2)
				{
					Minion m = enemyField[posi.row][posi.column];
					if(m!=null && m.Hp>=1)
					{
						targets.add(m);
					}
				}
			}
			return targets;
			
		}
		
		
		return targets;
	}
	
	
	public ArrayList<Minion> getTargets(Minion[][] enemyField , ArrayList<Minion> idols, Board b)
	{
		ArrayList<UPosition> posis = Board.getAttackPositions(this);
		
		return getTargets(enemyField, posis, idols, b);
	}
	
	
	
	public void addCardAsEnchantment(String type, String bname, String description, Minion card, Board b)
	{
		card.buffName = bname;
		card.bufftype =type;
		card.buffDescription = description;
		card.attachedCards.clear();
		card.owner = this;//to know the owner of this card
		
		addEnchantmentToList(card, b);
		
	}
	
	public void addnewEnchantments(String type, String name, String description, Card c, Board b, UColor owner)
	{
		addnewEnchantments( type,  name,  description,  c,  b,  owner, 0);
	}
	
	public void addnewEnchantments(String type, String name, String description, Card c, Board b, UColor owner, int turnCounter)
	{
		Minion buff = new Minion(type, name, description, c, owner);
		
		buff.attachedCards.clear();
		buff.owner = this;//to know the owner of this card
		this.turnCounter = 0;
		addEnchantmentToList(buff, b);
	}
	
	public void addnewPoison(Board b, UColor owner)
	{
		//TODO add poison + course cardtypes!
		
		Card c = new Card();
		c.typeId = 5; //5 is a free type!
		this.addnewEnchantments("BUFF", "Poison", "Unit takes 1 damage at the beginning of owner's turn.", c, b, owner);
	}
	
	public void addnewCurse(Board b, UColor owner, int amountt)
	{
		int amount = amountt;
		Card c = new Card();
		if(amount >=4) amount = 3;
		if(amount <=0) amount = 1;
		c.typeId = 5+amount; //6, 7, 8 are free types!
		this.addnewEnchantments("BUFF", "Curse " + amount, "Physical and poison damage dealt to this unit is increased by "+amount+".", c, b, owner);
		this.curse+=amount;
	}
	
	
	public void buffMinionWithoutMessage(int APbuff, int HPbuff, int ACbuff, Board b)
	{
		int oldAc=this.Ac;
		this.Ap +=APbuff;
		this.Hp += HPbuff;
		this.maxHP += HPbuff;
		if(this.Ac >=0 && this.maxAc >=1) this.Ac +=ACbuff;
		if(this.Ac<=0 && this.maxAc >=1) this.Ac=0;
		//b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
		
		if(this.Ac==0 && oldAc>=1) this.card.cardSim.onCountdownReachesZero(b, this);
	}
	
	public void buffMinion(int APbuff, int HPbuff, int ACbuff, Board b)
	{
		int oldAc=this.Ac;
		this.Ap +=APbuff;
		this.Hp += HPbuff;
		this.maxHP += HPbuff;
		if(this.Ac >=0 && this.maxAc >=1) this.Ac +=ACbuff;
		if(this.Ac<=0 && this.maxAc >=1) this.Ac=0;
		
		if(this.Hp <=0 )
		{
			
			String s= "{\"RemoveUnit\":{\"tile\":"+this.position.posToString()+",\"removalType\":\"DESTROY\"}}";
			b.addMessageToBothPlayers(s);
			b.addMinionToGrave(this);
		}
		else
		{
			b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
			if(this.Ac==0 && oldAc>=1) this.card.cardSim.onCountdownReachesZero(b, this);
		}
		
		
	}
	
	
	public void healMinion(int value, Board b)
	{
		if(this.Hp <=0) return;
		
		int oldhp = this.Hp;
		this.Hp = Math.min(this.maxHP, this.Hp += value);
		
		int heal = this.Hp - oldhp;
		
		
		
		//remove poison
		ArrayList<Minion> temp = new ArrayList<Minion>(this.attachedCards);
		for(Minion e : temp)
		{
			//100 = rangers bane!
			if(e.typeId ==5 || e.typeId == 100)//we use the free typeid of 5 to mark poison (faster than string comparison)
			{
				this.attachedCards.remove(e);
			}
		}
		
		//add heal message
		b.addMessageToBothPlayers(b.getMinionHealMessage(this, heal));
		//add status message
		if(heal>=1) b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
		
		//TODO healtriggers?
		
	}
	
	
	public void turnEndingDebuffing(Board b)
	{
		
		ArrayList<Minion> temp = new ArrayList<Minion>(this.attachedCards);
		for(Minion m : temp)
		{
			if(m.card.cardSim.onTurnEndsTrigger(b, m, b.activePlayerColor))
			{
				this.removeEnchantment(m, true, b);
			}
		}
		
	}
	
	public boolean hasPotionOfResistance(Board b)
	{
		if(this.card.cardSim.reduceDmgToOne(b, this)) return true; //waking stone
		for(Minion e : this.attachedCards)
		{
			if(e.card.cardSim.reduceDmgToOne(b, e)) 
			{
				return true;
			}

		}
		
		return false;
	}
	
	
	private void addEnchantmentToList(Minion ench, Board b)
	{
		boolean start = this.hasEnchantment();
		
		this.attachedCards.add(ench);
		
		String s = "{\"EnchantUnit\":{\"target\":{\"color\":\""+Board.colorToString(this.position.color)+"\",\"position\":\""+this.position.row+","+this.position.column+"\"},\"enchantTags\":[]}}";
		b.addMessageToBothPlayers(s);
		
		if(this.Hp <=0 )
		{
			
			s= "{\"RemoveUnit\":{\"tile\":"+this.position.posToString()+",\"removalType\":\"DESTROY\"}}";
			b.addMessageToBothPlayers(s);
			b.doDeathRattles2(this, 0, AttackType.UNDEFINED, DamageType.TERMINAL);
			
		}
		else
		{
			b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
		}
		
		boolean end = this.hasEnchantment();
		
		boolean newench = false;
		if(start == false && end == true)
		{
			newench = true;	
		}
		//DO unit got enchanted trigger!
		for(Minion mnn : b.getAllMinionOfField())
		{
			mnn.card.cardSim.onUnitGotEnchantment(b, mnn, this, newench);
		}
	}
	
	public void removeEnchantment(Minion e, boolean writeUpdateMessage, Board b)
	{
		boolean start = this.hasEnchantment();
		
		this.attachedCards.remove(e);
		//ArrayList<Minion> temp = new ArrayList<Minion>(this.attachedCards);
		//for(Minion m:temp)
		e.card.cardSim.onDeathrattle(b, e, e, AttackType.UNDEFINED, DamageType.TERMINAL);
		
		if(e.cardID>=0) 
		{
			b.addMinionToGrave(e);
		}
		if(writeUpdateMessage) 
		{
			b.addMessageToBothPlayers(b.getStatusUpdateMessage(this));
		}
		e.owner=null;
		
		boolean end = this.hasEnchantment();
		
		if(start == false && end == true)
		{
			for(Minion mnn : b.getAllMinionOfField())
			{
				mnn.card.cardSim.onUnitLostAllEnchantments(b, mnn, this);
			}
		}
		
	}
	
	public ArrayList<Minion> getAttachedCards()//we return a copy, so enchantments could be deleted!
	{
		return new ArrayList<Minion>(this.attachedCards);
	}

	public ArrayList<SubType> getSubTypes()//we return a copy, so enchantments could be deleted!
	{
		return new ArrayList<SubType>(this.subtypes);
	}
	
	//subtype added is called!
	//TODO trigger LingerSpells
	public void addSubtype(SubType sub, Board b)
	{
		boolean dotrigger = true;
		if(this.subtypes.contains(sub)) dotrigger = false;
		this.subtypes.add(sub);
		
		if(dotrigger)
		{
			for(Minion m : b.getAllMinionOfField())
			{
				m.card.cardSim.onSubTypeAdded(b, m, this, sub);
				for(Minion e : m.getAttachedCards())
				{
					e.card.cardSim.onSubTypeAdded(b, e, this, sub);
				}
				
			}
			
			for(Minion rule : b.getAllRulesWithColorFirst(this.position.color))
			{
				rule.card.cardSim.onSubTypeAdded(b, rule, this, sub);
			}
			
		}
		
	}
	
	//dont trigger if another subtype is there!
	public void removeSubtype(SubType sub, Board b)
	{
		if(!this.subtypes.contains(sub)) return;//ERROR minion doesnt has this subtype
		
		boolean dotrigger = true;
		//delete last matching subtype:
		int i = -1;
		int j = 0;
		for(SubType su : this.subtypes)
		{
			if(su == sub) i=j;
			j++;
		}
		
		this.subtypes.remove(i);
		
		//after removing the subtype it still has this subtype... subtypes doesnt change
		if(this.subtypes.contains(sub)) dotrigger = false;
		
		if(dotrigger)
		{
			for(Minion m : b.getAllMinionOfField())
			{
				m.card.cardSim.onSubTypeDeleted(b, m, this, sub);
				for(Minion e : m.getAttachedCards())
				{
					e.card.cardSim.onSubTypeDeleted(b, e, this, sub);
				}
			}
			
			for(Minion rule : b.getAllRulesWithColorFirst(this.position.color))
			{
				rule.card.cardSim.onSubTypeAdded(b, rule, this, sub);
			}
		}
		
	}


}
