package BattleStuff;

import java.util.ArrayList;

public class ActiveAbility {

	public activeAbilitys id = activeAbilitys.Move;
	public int costDecay=0;
	public int costOrder=0;
	public int costGrowth=0;
	public int costEnergy=0;
	public int costSpecial=0;
	
	public ActiveAbility(String name, int g, int o, int e, int d, int s)
	{
		if(name.equals("Move")) {this.id = activeAbilitys.Move;}
		if(name.equals("SummonWolf")) {this.id = activeAbilitys.SummonWolf;}
		if(name.equals("SacrificeAttack")) {this.id = activeAbilitys.SacrificeAttack;}
		if(name.equals("TribalMemorialAbility")) {this.id = activeAbilitys.TribalMemorialAbility;}
		if(name.equals("DrawScrollAbility")) {this.id = activeAbilitys.DrawScrollAbility;}
		if(name.equals("GrowthRegenerateAbility")) {this.id = activeAbilitys.GrowthRegenerateAbility;}
		if(name.equals("ToolInitiate")) {this.id = activeAbilitys.ToolInitiate;}
		if(name.equals("SacrificeSelf")) {this.id = activeAbilitys.SacrificeSelf;}
		if(name.equals("CursemongerAbility")) {this.id = activeAbilitys.CursemongerAbility;}
		if(name.equals("EnergyCountdownAbility")) {this.id = activeAbilitys.EnergyCountdownAbility;}
		if(name.equals("Flying")) {this.id = activeAbilitys.Flying;}
		if(name.equals("MysticAltar")) {this.id = activeAbilitys.MysticAltar;}
		if(name.equals("Stitcher")) {this.id = activeAbilitys.Stitcher;}
		if(name.equals("FulminationConduit")) {this.id = activeAbilitys.FulminationConduit;}
		if(name.equals("MarksmanAttack")) {this.id = activeAbilitys.MarksmanAttack;}
		if(name.equals("DrawEnchantment")) {this.id = activeAbilitys.DrawEnchantment;}
		if(name.equals("BannerOfOrdinance")) {this.id = activeAbilitys.BannerOfOrdinance;}
		if(name.equals("WingsSorceress")) {this.id = activeAbilitys.WingsSorceress;}
		if(name.equals("Uhu")) {this.id = activeAbilitys.Uhu;}
		if(name.equals("SnarglOmelette")) {this.id = activeAbilitys.SnarglOmelette;}
		if(name.equals("WindupAutomaton")) {this.id = activeAbilitys.WindupAutomaton;}
		
		this.costGrowth=g;
		this.costOrder=o;
		this.costEnergy=e;
		this.costDecay=d;
		this.costSpecial =s;
	}
	
	public void payEnergy(Board b, Minion m)
	{
		int summe= this.costGrowth+this.costOrder+this.costEnergy+this.costDecay+this.costSpecial;
		if(m.typeId == 207) summe = 3;
		if(m.typeId == 204) summe = 2;
		if(m.typeId == 812) summe = 3;
		if(summe==0)return;
		
		int neededWild = 0;
		//better:
		int[] cressis = b.blackcurrentRessources;
		if(m.position.color == UColor.white) 
		{
			cressis= b.whitecurrentRessources;
		}
		
		int cost = this.costGrowth;
		if(cressis[0] < cost )
		{
			neededWild = cost - cressis[0];
			
		}
		cressis[0]-=cost;
		
		cost = this.costOrder;
		if(cressis[1] < cost)
		{
			neededWild = cost - cressis[1];
			
		}
		cressis[1]-=cost;
		
		cost = this.costEnergy;
		if(m.typeId == 207)
		{
			cost =  3;
		}
		if(m.typeId == 204)
		{
			cost =  2;
		}
		if(m.typeId == 812)
		{
			cost =  3;
		}
		if(cressis[2] < cost )
		{
			neededWild = cost - cressis[2];
			
		}
		cressis[2]-=cost;
		
		cost = this.costDecay;
		if(cressis[3] < cost )
		{
			neededWild = cost - cressis[3];
			
		}
		cressis[3]-=cost;
		
		neededWild += this.costSpecial;
		
		cressis[4]-=neededWild;
		
		for(int ii=0;ii<5;ii++)
		{
			cressis[ii] = Math.max(0, cressis[ii]);
		}
		
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		return;
	}
	
	public Boolean hasEnoughRessis(Board b, Minion m)
	{
		Boolean isp = true;
		Boolean hasEnoughResources=false;
		//better:
		int[] cressis = b.blackcurrentRessources;
		if(m.position.color == UColor.white) 
		{
			cressis= b.whitecurrentRessources;
		}
		
		int neededWild = 0;
		
		int cost = this.costGrowth;
		if(cressis[0] < cost )
		{
			neededWild = cost - cressis[0];
		}
		
		cost = this.costOrder;
		if(cressis[1] < cost)
		{
			neededWild = cost - cressis[1];
		}
		
		cost = this.costEnergy;
		if(m.typeId == 207)
		{
			cost =  3;
		}
		if(m.typeId == 204)
		{
			cost =  2;
		}
		if(m.typeId == 812)
		{
			cost =  3;
		}
		if(cressis[2] < cost )
		{
			neededWild = cost - cressis[2];
		}
		
		cost = this.costDecay;
		if(cressis[3] < cost )
		{
			neededWild = cost - cressis[3];
		}
		
		neededWild += this.costSpecial;
		
		if(cressis[4] >= neededWild)
		{
			hasEnoughResources=true;
		}
		
		if(!hasEnoughResources) 
		{
			//TODO error
			return false;
		}
		
		return true;

	}
	
	
	public Boolean isPlayAble(Board b, Minion m, ArrayList<UPosition> poses, ArrayList<UPosition> poses2)
	{
		Boolean isp = false;
		if(this.id == activeAbilitys.Move)
		{
			return m.canMove() && poses.size()>=1;
		}
		if(this.id == activeAbilitys.SummonWolf)
		{
			if(m.getAc() == 0 && poses.size()>=1) return true;
			return false;
		}
		if(this.id == activeAbilitys.TribalMemorialAbility)//TODO or return false if energy is to low?
		{
			return true;
		}
		if(this.id == activeAbilitys.ToolInitiate)
		{
			if(m.getAc() == 0 && poses.size()>=1) return true;
			return false;
		}
		if(this.id == activeAbilitys.GrowthRegenerateAbility)
		{
			int[] curE = b.whitecurrentRessources;
			if(m.position.color == UColor.black) 
			{
				curE = b.blackcurrentRessources;
			}
			if(curE[0] >= 1 && m.Hp < m.maxHP) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.DrawScrollAbility)
		{
			if(m.getAc() == 0) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.SacrificeAttack)
		{
			if(m.getAc() == 0 && poses.size()>=1) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.SacrificeSelf)
		{
			if(m.getAc() == 0) return true;
			return false;
		}
		if(this.id == activeAbilitys.CursemongerAbility)
		{
			if(m.getAc() == 0 && poses.size()>=1) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.Flying)
		{
			if(m.canMove() && poses.size()>=1) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.EnergyCountdownAbility)
		{
			if(m.getAc()>=1) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.MysticAltar)
		{
			if(m.getAc() == 0 && poses.size()>=1)
			{
				return true;
			}
			return false;
		}
		
		if(this.id == activeAbilitys.DrawEnchantment)
		{
			if(m.getAc() == 0) return true;
			return false;
		}
		
		if(this.id == activeAbilitys.Stitcher)
		{
			if(m.getAc() == 0 && poses.size()>=1 && poses2.size()>=1) return true;
			return false;
		}
		
		return isp;
	}
	

	public Boolean needPosition(Board b, Minion m)
	{
		if(this.id == activeAbilitys.Move)
		{
			return true;
		}
		if(this.id == activeAbilitys.SummonWolf)
		{
			return true;
		}
		if(this.id == activeAbilitys.TribalMemorialAbility)
		{
			return false;
		}
		if(this.id == activeAbilitys.ToolInitiate)
		{
			return true;
		}
		if(this.id == activeAbilitys.GrowthRegenerateAbility)
		{
			return false;
		}
		if(this.id == activeAbilitys.DrawScrollAbility)
		{
			return false;
		}
		if(this.id == activeAbilitys.SacrificeAttack)
		{
			return true;
		}
		if(this.id == activeAbilitys.SacrificeSelf)
		{
			return false;
		}
		if(this.id == activeAbilitys.CursemongerAbility)
		{
			return true;
		}
		if(this.id == activeAbilitys.Flying)
		{
			return true;
		}
		if(this.id == activeAbilitys.EnergyCountdownAbility)
		{
			return false;
		}
		if(this.id == activeAbilitys.MysticAltar)
		{
			return true;
		}
		if(this.id == activeAbilitys.DrawEnchantment)
		{
			return false;
		}
		if(this.id == activeAbilitys.Stitcher)
		{
			return true;

		}
		return false;
	}
	
	public Boolean needPosition2(Board b, Minion m)
	{
		if(this.id == activeAbilitys.Stitcher)
		{
			return true;

		}
		return false;
	}
	
	public ArrayList<UPosition> getPositions(Board b, Minion m)
	{
		ArrayList<UPosition> isp = new ArrayList<UPosition>();
		UPosition mp = new UPosition(m.position);
		
		if(this.id == activeAbilitys.Move)
		{
			isp.addAll(b.getMovePositions(m.position.color, m.position.row, m.position.column));
			return isp;
		}
		
		
		
		if(this.id == activeAbilitys.SummonWolf)
		{
			ArrayList<UPosition> nbrs = mp.getNeightbours();
			ArrayList<Minion> mins = b.getMinionsFromPositions(nbrs);
			
			for(UPosition pp : nbrs)
			{
				Boolean isEmpty = true;
				for(Minion mi : mins)
				{
					if(mi.position.isEqual(pp))
					{
						isEmpty=false;
					}
				}
				
				if(isEmpty) isp.add(pp);
			}
			return isp;
		}
		
		if(this.id == activeAbilitys.TribalMemorialAbility)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.ToolInitiate)
		{
			ArrayList<Minion> mins = b.getPlayerFieldList(m.position.color);
			for(Minion pp : mins)
			{
				if(pp.cardType == Kind.STRUCTURE && pp.maxAc>=1)
				{
					isp.add(new UPosition(pp.position));
				}
			}
			return isp;
		}
		
		if(this.id == activeAbilitys.GrowthRegenerateAbility)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.DrawScrollAbility)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.SacrificeAttack)
		{
			for(Minion mm : b.getAllMinionOfField())
			{
				isp.add(new UPosition(mm.position));
			}
			return isp;
		}
		
		if(this.id == activeAbilitys.SacrificeSelf)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.CursemongerAbility)
		{
			for(Minion mm : b.getAllMinionOfField())
			{
				isp.add(new UPosition(mm.position));
			}
			return isp;
		}
		
		if(this.id == activeAbilitys.Flying)
		{
			isp.addAll(b.getFreePositions(m.position.color));
			return isp;
		}
		
		if(this.id == activeAbilitys.EnergyCountdownAbility)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.MysticAltar)
		{
			ArrayList<Minion> mins = b.getPlayerFieldList(m.position.color);
			for(Minion pp : mins)
			{
				if(pp.cardType == Kind.CREATURE)
				{
					isp.add(new UPosition(pp.position));
				}
			}
			return isp;
		}
		
		if(this.id == activeAbilitys.DrawEnchantment)
		{
			return isp;
		}
		
		if(this.id == activeAbilitys.Stitcher)
		{
			ArrayList<Minion> mins = b.getPlayerFieldList(m.position.color);
			for(Minion pp : mins)
			{
				if(pp.cardType == Kind.CREATURE)
				{
					isp.add(new UPosition(pp.position));
				}
			}
			return isp;
		}
		
		return isp;
	}
	
	
	public ArrayList<UPosition> getSecondPositions(Board b, Minion m)
	{
		ArrayList<UPosition> isp = new ArrayList<UPosition>();
		
		if(this.id == activeAbilitys.Stitcher)
		{
			ArrayList<Minion> mins = b.getPlayerFieldList(m.position.color);
			for(Minion pp : mins)
			{
				if(pp.cardType == Kind.CREATURE)
				{
					isp.add(new UPosition(pp.position));
				}
			}
			return isp;
		}
		
		return isp;
	}
	
}
