package BattleStuff;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import ServerStuff.InterThreadStuff;
import SimulationCards.Simtemplate;


public class CardDB {

	
	private static CardDB instance;
	public static CardDB getInstance()
    {
		if (instance == null)
        {
            instance = new CardDB();
        }
        return instance;
    }

	private CardDB()
	{

	}
	
	public HashMap<Integer, Card> cardId2Card = new HashMap<Integer, Card>();
	
	public void init(String cardinfomsg)
	{
		JSONObject jo = new JSONObject(cardinfomsg);
		JSONArray cardTypes = jo.getJSONArray("cardTypes");
		for(int i=0; i< cardTypes.length(); i++ )
		{
			JSONObject ct = cardTypes.getJSONObject(i);
			
			int id = ct.getInt("id");
			
			String name = ct.getString("name");
            //name = name.replace(" ", "");
            //name = name.replace("'", "");
            //name = name.replace(",", "");
            //name = name.replace("-", "");
            
            String knd = ct.getString("kind");
            
            

            int rarity = ct.getInt("rarity");
            int ap = ct.getInt("ap");
            int hp = ct.getInt("hp");
            int ac = ct.getInt("ac");
            int costDecay = ct.getInt("costDecay");
            int costOrder = ct.getInt("costOrder");
            int  costGrowth = ct.getInt("costGrowth");
            int costEnergy = ct.getInt("costEnergy");
            
            ArrayList<subType> subtypes = new ArrayList<subType>();
            JSONArray su = ct.getJSONArray("subTypes");
            for (int ii = 0; ii < su.length(); ii++)
            {
                String subs = su.getString(ii);
                subType sub = subType.NONE;
                
                if(subs.equals("Gravelock")) {sub = subType.Gravelock;}
                if(subs.equals("Human")) {sub = subType.Human;}
                if(subs.equals("Kinfolk")) {sub = subType.Kinfolk;}
                if(subs.equals("Artillery")) {sub = subType.Artillery;}
                if(subs.equals("Destruction")) {sub = subType.Destruction;}
                if(subs.equals("Wall")) {sub = subType.Wall;}
                if(subs.equals("Displacement")) {sub = subType.Displacement;}
                if(subs.equals("Beast")) {sub = subType.Beast;}
                if(subs.equals("Wolf")) {sub = subType.Wolf;}
                if(subs.equals("Elder")) {sub = subType.Elder;}
                if(subs.equals("Totem")) {sub = subType.Totem;}
                if(subs.equals("Knight")) {sub = subType.Knight;}
                if(subs.equals("Automaton")) {sub = subType.Automaton;}
                if(subs.equals("Tribesman")) {sub = subType.Tribesman;}
                if(subs.equals("Memorial")) {sub = subType.Memorial;}
                if(subs.equals("Soldier")) {sub = subType.Soldier;}
                if(subs.equals("Bunny")) {sub = subType.Bunny;}
                if(subs.equals("Vaettr")) {sub = subType.Vaettr;}
                if(subs.equals("Mystic")) {sub = subType.Mystic;}
                if(subs.equals("Undead")) {sub = subType.Undead;}
                if(subs.equals("Masked")) {sub = subType.Masked;}
                if(subs.equals("Rat")) {sub = subType.Rat;}
                if(subs.equals("Forge")) {sub = subType.Forge;}
                if(subs.equals("Lingering")) {sub = subType.Lingering;}
                if(subs.equals("Reaver")) {sub = subType.Reaver;}
                if(subs.equals("Rebel")) {sub = subType.Rebel;}
                if(subs.equals("Bear")) {sub = subType.Bear;}
                if(subs.equals("Anima")) {sub = subType.Anima;}
                if(subs.equals("Warrior")) {sub = subType.Warrior;}
                if(subs.equals("Warden")) {sub = subType.Warden;}
                if(subs.equals("Mercenary")) {sub = subType.Mercenary;}
                if(subs.equals("Cat")) {sub = subType.Cat;}

                
                subtypes.add(sub);
            }
            
            ArrayList<ActiveAbility> abilitys = new ArrayList<ActiveAbility>();
            if (ct.has("abilities"))
            {
                
            	JSONArray aa = ct.getJSONArray("abilities");
                for (int ii = 0; ii < aa.length(); ii++)
                {
                	JSONObject abs = aa.getJSONObject(ii);

                    String abid = abs.getString("id");
                    JSONObject costs = abs.getJSONObject("cost");
                    int g = costs.getInt("GROWTH");
                    int o = costs.getInt("ORDER");
                    int e = costs.getInt("ENERGY");
                    int d = costs.getInt("DECAY");
                    int s = costs.getInt("SPECIAL");
                    ActiveAbility aab = new ActiveAbility(abid, g, o, d, e, s);
                    abilitys.add(aab);
                }
            }
            
            ArrayList<String> passivs = new ArrayList<String>();
            if (ct.has("passiveRules"))
            {
                
            	JSONArray pp = ct.getJSONArray("passiveRules");
                for (int ii = 0; ii < pp.length(); ii++)
                {
                	JSONObject abs = pp.getJSONObject(ii);

                	String abid = abs.getString("displayName");
                    passivs.add(abid);
                }
            }
            
            String area = "UNDEFINED";
            if (ct.has("targetArea"))
            {
                 area = ct.getString("targetArea");
            }
            
            String desc = ct.getString("description");
            
            //create new card
            Card ncard = new Card();
            ncard.typeId = id;
            ncard.cardname = name;
            ncard.cardDescription = desc;
            ncard.rarity = rarity;
            
            ncard.ac = ac;
            ncard.ap= ap;
            ncard.hp = hp;
            ncard.costDecay = costDecay;
            ncard.costEnergy = costEnergy;
            ncard.costOrder = costOrder;
            ncard.costGrowth = costGrowth;
            
            ncard.abilitys = abilitys;
            ncard.passiveAbilitys = passivs;
            ncard.subtypes = subtypes;
            
            ncard.trgtAreaString = area;
            
            if(area.equals("FORWARD")) {ncard.trgtArea  = targetArea.FORWARD;}
            if(area.equals("RADIUS_7")) {ncard.trgtArea  = targetArea.RADIUS_7;}
            if(area.equals("TILE")) {ncard.trgtArea  = targetArea.TILE;}
            if(area.equals("UNDEFINED")) {ncard.trgtArea  = targetArea.UNDEFINED;}
            if(area.equals("RADIUS_4")) {ncard.trgtArea  = targetArea.RADIUS_4;}
            if(area.equals("SEQUENTIAL")) {ncard.trgtArea  = targetArea.SEQUENTIAL;}
            if(area.equals("ROW_FULL_IDOLS")) {ncard.trgtArea  = targetArea.ROW_FULL_IDOLS;}
            if(area.equals("ROW_SIDE")) {ncard.trgtArea  = targetArea.ROW_SIDE;}

            if(knd.equals("NONE")) {ncard.cardKind  = Kind.NONE;}
            if(knd.equals("CREATURE")) {ncard.cardKind  = Kind.CREATURE;}
            if(knd.equals("ENCHANTMENT")) {ncard.cardKind  = Kind.ENCHANTMENT;}
            if(knd.equals("SPELL")) {ncard.cardKind  = Kind.SPELL;}
            if(knd.equals("STRUCTURE")) {ncard.cardKind  = Kind.STRUCTURE;}
            
            ncard.cardSim = Simtemplate.intToSimtemplate(id);
            
            this.cardId2Card.put(id, ncard);
            
		}
		
	}
	
}
