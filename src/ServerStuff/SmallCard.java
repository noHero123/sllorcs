package ServerStuff;

public class SmallCard {
	
	//( typeId int, owner int, tradeable int, level int)
	public long cardid=-1;
	public int typeid=-1;
	public int tradeable = 0;//0=not, 1 ok
	public long owner= -1;
	public int level = 0;

	public void print()
	{
		System.out.println(cardid + " " + typeid+ " " + owner);
	}
	
}
