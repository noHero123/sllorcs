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
	
	
	public Boolean isPlayAble(Board b, Minion m, ArrayList<Position> poses)
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
		
		return isp;
	}
	
	public Boolean hasEnoughRessis(Board b, Minion m)
	{
		Boolean isp = false;
		if(this.id == activeAbilitys.Move)
		{
			return true;
		}
		if(this.id == activeAbilitys.SummonWolf)
		{
			return true;
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
		
		return false;
	}
	
	public ArrayList<Position> getPositions(Board b, Minion m)
	{
		ArrayList<Position> isp = new ArrayList<Position>();
		Position mp = new Position(m.position);
		
		if(this.id == activeAbilitys.Move)
		{
			isp.addAll(b.getMovePositions(m.position.color, m.position.row, m.position.column));
			return isp;
		}
		
		if(this.id == activeAbilitys.SummonWolf)
		{
			ArrayList<Position> nbrs = mp.getNeightbours();
			ArrayList<Minion> mins = b.getMinionsFromPositions(nbrs);
			
			for(Position pp : nbrs)
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
		
		return isp;
	}
	
	
}
