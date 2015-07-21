package BattleStuff;

import java.util.ArrayList;
import java.util.Iterator;

public class Minion {

	public Card card= new Card();
	public Kind cardType = Kind.NONE;
	public long cardID = -1;
	public int typeId = 0;
	public ArrayList<subType> subtypes = new ArrayList<subType>();
	
	public int Ap = 0;
	public int Hp =0;
	private int Ac =-1;//please dont change this :D
	public int maxHP=0;
	public int maxAc=0;
	
	public int movesThisTurn = 0;
	public int basicMove = 1;
	public int moveChanges=0;
	public int tempMove=0;
	
	public boolean isRelentless = false;
	public boolean hasPiercing = false; // it doesnt have to be on card, can also be enchantment!
	
	public AttackType attackType = AttackType.MELEE;
	public int armor = 0;
	public int curse = 0;
	//public int spiky = 0;
	public boolean isPoisonous = false;
	public int magicRessi=0;
	
	public boolean isToken = true;
	
	//TODO save in position variable
	public Position position = new Position(Color.white, -1, -1);
	//public Color color = Color.white;
	//public int row = -1; // 0-4
	//public int column = -1;// 0-2
	public int lvl=0;
	
	public String bufftype ="";
	public String buffDescription ="";
	public String buffName ="";
	
	public int lingerDuration=-1;
	
	public ArrayList<Minion> attachedCards = new ArrayList<Minion>();
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
	public boolean addToHandAfterDead = true;
	
	public int frostbeardCounter=0;
	public int ducalInfCounter=0;
	public int royalInfCounter=0;
	
	public int getAc()
	{
		return this.Ac;
	}
	
	public void resetAc()
	{
		this.Ac = this.maxAc;
	}
	
	public void setAc(int v)
	{
		this.Ac = v;
		this.maxAc=v;
	}
	
	public Minion(Card c, long cid, Color playercol)
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
		for(subType s : c.subtypes)
		{
			this.subtypes.add(s);
		}
		frostbeardCounter=0;
		ducalInfCounter=0;
		royalInfCounter=0;
	}
	
	public Minion getMinionToken()
	{
		Minion temp = new Minion(this.card, -1, this.position.color);

		temp.isToken =true;
		
		return temp;
	}
	
	
	public Minion(int maxHP, Color col, int pos)
	{
		this.Hp = maxHP;
		this.maxHP = maxHP;
		this.isToken =false;
		this.position.color = col;
		this.isIdol=true;
		this.position.row = pos;
		this.position.column = 4;
	}
	
	public Minion (String type, String name, String description, Card c, Color ownercolor)
	{
		this.bufftype = type;
		this.buffName = name;
		this.buffDescription = description;
		this.card = c;
		this.typeId = c.typeId;
		this.position.color = ownercolor;
		this.isToken=true;
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
		this.armor = this.card.cardSim.getArmor(b, this);
		this.isRelentless = this.card.cardSim.isRelentless(b, this);
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
		for(subType s : this.card.subtypes)
		{
			this.subtypes.add(s);
		}
		this.moveChanges=0;
		this.armor=0;
		this.isRelentless=false;
		
		numberOfDmgTaken=0;
		deadTriggersDone=false;
		currentAttackPlus = 0; // for kinfolk jarl 
		turnsInplay=0;
		aoeDmgToDo=0;
		desperationBuffs=0;
		imuneToNextDmg=false;
		addToHandAfterDead = true;
		this.attachedCards.clear();
		this.owner = null;
		frostbeardCounter=0;
		ducalInfCounter=0;
		royalInfCounter=0;
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
		boolean rel =  this.isRelentless || this.card.cardSim.isRelentless(b, this);
		
		for(Minion e: this.attachedCards)
		{
			rel = rel || e.card.cardSim.isRelentless(b, e);
		}
		
		return rel;
	}
	
	public ArrayList<Minion> getTargets(Minion[][] enemyField, ArrayList<Position> posis, ArrayList<Minion> idols)
	{
		ArrayList<Minion> targets = new ArrayList<Minion>();
		if(this.card.trgtArea == targetArea.FORWARD)
		{
			if(this.hasPiercing) 
			{
				int currentattack = this.Ap;
				for(Position posi : posis)
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
			
			for(Position posi : posis)
			{
				if(posi.row>=0 && posi.row <=4 && posi.column>=0 && posi.column <=2)
				{
					Minion m = enemyField[posi.row][posi.column];
					if(m!=null && m.Hp>=1)
					{
						targets.add(m);
						return targets;
					}
				}
			}
			
			//no enemy on line -> attack idol
			targets.add( idols.get(this.position.row) );
			
			return targets;
		}
		
		if(this.card.trgtArea == targetArea.RADIUS_4)
		{
			
			for(Position posi : posis)
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
	
	
	public ArrayList<Minion> getTargets(Minion[][] enemyField , ArrayList<Minion> idols)
	{
		ArrayList<Position> posis = Board.getAttackPositions(this);
		
		return getTargets(enemyField, posis, idols);
	}
	
	public void addCardAsEnchantment(String type, String bname, String description, Minion card, Board b)
	{
		card.buffName = bname;
		card.bufftype =type;
		card.buffDescription = description;
		card.attachedCards.clear();
		card.owner = this;//to know the owner of this card
		
		this.attachedCards.add(card);
		
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
	}
	
	public void addnewEnchantments(String type, String name, String description, Card c, Board b, Color owner)
	{
		Minion buff = new Minion(type, name, description, c, owner);
		
		buff.attachedCards.clear();
		buff.owner = this;//to know the owner of this card
		
		this.attachedCards.add(buff);
		
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
	}
	
	public void addnewPoison(Board b, Color owner)
	{
		
		Card c = new Card();
		this.addnewEnchantments("BUFF", "Poison", "Unit takes 1 damage at the beginning of owner's turn.", c, b, owner);
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
		//in c# i would do it this way:
		/*
		ArrayList<Minion> attc = new ArrayList<Minion>(this.attachedCards);
		for(Minion ench : attc)
		{
			if(ench.buffName.equals("Poison"))
			{
				this.attachedCards.remove(ench);
			}
		}*/
		
		//in java i do it this way :D
		for (Iterator<Minion> iterator = this.attachedCards.iterator(); iterator.hasNext();) 
		{
			Minion string = iterator.next();
		    if (string.buffName.equals("Poison")) 
		    {
		        iterator.remove();
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
		//no status update needed, its done witch AC countdown
		for (Iterator<Minion> iterator = this.attachedCards.iterator(); iterator.hasNext();) 
		{
			Minion attc = iterator.next();
			if(attc.card.cardSim.onTurnEndsTrigger(b, attc, b.activePlayerColor))
			{
				iterator.remove();
				
				b.addMinionToGrave(attc);
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
	
	
}
