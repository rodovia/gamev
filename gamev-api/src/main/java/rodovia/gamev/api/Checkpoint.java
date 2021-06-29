package rodovia.gamev.api;

import org.bukkit.Location;

public class Checkpoint {
	private Location loc;
	private int ident;
	
	public Checkpoint(Location where, int id) {
		loc = where;
		ident = id;
	}
	
	public int id() {
		return ident;
	}
	
	public Location where() {
		return loc;
	}
}
