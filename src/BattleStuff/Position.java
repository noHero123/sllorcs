package BattleStuff;

import java.util.ArrayList;

public class Position {

	public Color color= Color.white;
	public int row = 0;
	public int column = 0;
	
	public Position(Color c, int r, int cmn)
	{
		this.color = c; this.row = r; this.column=cmn;
	}
	
	public Position(Position p)
	{
		this.color = p.color; this.row = p.row; this.column=p.column;
	}
	
	public String posToString()
	{
		String c = "white";
		if(color==Color.black) c="black";
		//{"color":"white","position":"0,0"}
		return "{\"color\":\""+ c +"\",\"position\":\"" + row + "," +  column+ "\"}";
	}
	
	public boolean isNeightbour(Position p)
	{
		
		boolean isit= false;
		for(Position pipi : this.getNeightbours())
		{
			if(pipi.color == p.color && pipi.column == p.column && pipi.row == p.row) isit=true;
		}
		return isit;
	}
	
	
	public boolean isEqual(Position p)
	{
		if(this.color == p.color && this.row == p.row && this.column == p.column) return true;
		
		return false;
	}
	
	
	
	public ArrayList<Position> getNeightbours()
	{
		//TODO make more efficient!
		ArrayList<Position> posMoves = new ArrayList<Position>();
		if(this.row==0)
		{
			if(this.column == 0) 
				{
					posMoves.add(new Position(color, 0,1));
					posMoves.add(new Position(color, 1,0));
				}
			if(this.column == 1) 
			{
				posMoves.add(new Position(color, 0,0));
				posMoves.add(new Position(color, 0,2));
				posMoves.add(new Position(color, 1,0));
				posMoves.add(new Position(color, 1,1));
			}
			if(this.column == 2) 
			{
				posMoves.add(new Position(color, 0,1));
				posMoves.add(new Position(color, 1,1));
				posMoves.add(new Position(color, 1,2));
			}
		}
		
		if(this.row==1)
		{
			if(this.column == 0) 
				{
					posMoves.add(new Position(color, 0,0));
					posMoves.add(new Position(color, 0,1));
					posMoves.add(new Position(color, 1,1));
					posMoves.add(new Position(color, 2,0));
					posMoves.add(new Position(color, 2,1));
				}
			if(this.column == 1) 
			{
				posMoves.add(new Position(color, 0,1));
				posMoves.add(new Position(color, 0,2));
				posMoves.add(new Position(color, 1,0));
				posMoves.add(new Position(color, 1,2));
				posMoves.add(new Position(color, 2,1));
				posMoves.add(new Position(color, 2,2));
			}
			if(this.column == 2) 
			{
				posMoves.add(new Position(color, 0,2));
				posMoves.add(new Position(color, 1,1));
				posMoves.add(new Position(color, 2,2));
			}
		}
		
		if(this.row==2)
		{
			if(this.column == 0) 
				{
				posMoves.add(new Position(color, 1,0));
				posMoves.add(new Position(color, 2,1));
				posMoves.add(new Position(color, 3,0));
				}
			if(this.column == 1) 
			{
				posMoves.add(new Position(color, 1,0));
				posMoves.add(new Position(color, 1,1));
				posMoves.add(new Position(color, 2,0));
				posMoves.add(new Position(color, 2,2));
				posMoves.add(new Position(color, 3,0));
				posMoves.add(new Position(color, 3,1));
			}
			if(this.column == 2) 
			{
				posMoves.add(new Position(color, 1,1));
				posMoves.add(new Position(color, 1,2));
				posMoves.add(new Position(color, 2,1));
				posMoves.add(new Position(color, 3,1));
				posMoves.add(new Position(color, 3,2));
			}
		}
		
		if(this.row==3)
		{
			if(this.column == 0) 
				{
					posMoves.add(new Position(color, 2,0));
					posMoves.add(new Position(color, 2,1));
					posMoves.add(new Position(color, 3,1));
					posMoves.add(new Position(color, 4,0));
					posMoves.add(new Position(color, 4,1));
				}
			if(this.column == 1) 
			{
				posMoves.add(new Position(color, 2,1));
				posMoves.add(new Position(color, 2,2));
				posMoves.add(new Position(color, 3,0));
				posMoves.add(new Position(color, 3,2));
				posMoves.add(new Position(color, 4,1));
				posMoves.add(new Position(color, 4,2));
			}
			if(this.column == 2) 
			{
				posMoves.add(new Position(color, 2,2));
				posMoves.add(new Position(color, 3,1));
				posMoves.add(new Position(color, 4,2));
			}
		}
		
		if(this.row==4)
		{
			if(this.column == 0) 
				{
					posMoves.add(new Position(color, 4,1));
					posMoves.add(new Position(color, 3,0));
				}
			if(this.column == 1) 
			{
				posMoves.add(new Position(color, 4,0));
				posMoves.add(new Position(color, 4,2));
				posMoves.add(new Position(color, 3,0));
				posMoves.add(new Position(color, 3,1));
			}
			if(this.column == 2) 
			{
				posMoves.add(new Position(color, 4,1));
				posMoves.add(new Position(color, 3,1));
				posMoves.add(new Position(color, 3,2));
			}
		}
		
		return posMoves;
	}
	
	
}
