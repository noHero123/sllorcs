package BattleStuff;

public enum tileSelector {//used for getting tiles for cardinfo-msg

	None, //for spells like rallying
	
	all, //select all tiles (like decimation)
	all_units, //targets all units: creatures + structures
	all_units_with_ac, //target all units with ac >=0 (all units that count down) used in thought trap
	all_units_with_enchantments, //target all units with ac >=0 (all units that count down) used in thought trap
	all_creatures_with_hp_less_or_equal_2, 
	all_creatures, //target all creatures
	all_structures, // all structures
	all_free, // all free tiles
	all_lobbers_or_ranged_units,
	all_ranged_creatures,
	all_melees, 
	
	opp_all,
	opp_units, 
	opp_creatures, 
	opp_structures, 
	opp_free,
	
	own_all,
	own_units, 
	own_creatures,
	own_beasts,
	own_structures,
	own_free,
	
}
