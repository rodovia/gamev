package rodovia.gamev.api.util.checkpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import rodovia.gamev.api.Checkpoint;

public class CheckpointManager {
	private Set<Checkpoint> checks;
	private Map<Checkpoint, Set<Player>> playerChecks;
	
	public CheckpointManager() {
		checks = new HashSet<>();
		playerChecks = new HashMap<>();
	}
	
	public Checkpoint getCheckpoint(int id) {
		Stream<Checkpoint> ch = checks.stream().filter((check) -> {
			return check.id() == id;
		});
		
		List<Checkpoint> check = ch.collect(Collectors.toCollection(ArrayList::new));
		return check.get(0);
	}
	
	public void removeCheckpoint(int id) {
		Checkpoint ch = getCheckpoint(id);
		checks.remove(ch);
	}
	
	public void addCheckpoint(Checkpoint checkp) {
		checks.add(checkp);
	}
	
	public void addPlayerCheckpoint(int id, Player who) {
		Checkpoint ch = getCheckpoint(id);
		if (ch == null) {
			throw new NullPointerException();
		}

		Set<Player> pla = playerChecks.get(ch);
		if (pla == null) {
			playerChecks.put(ch, new HashSet<>());
		}
		
		playerChecks.get(ch).add(who);
	}
	
	public Checkpoint getPlayerCheckpoint(Player who) {
		Set<Map.Entry<Checkpoint, Set<Player>>> ent = playerChecks.entrySet();
		
		while (ent.iterator().hasNext()) {
			Map.Entry<Checkpoint, Set<Player>> entry = ent.iterator().next();
			while (entry.getValue().iterator().hasNext()) {
				Player player = entry.getValue().iterator().next();
				if (player.equals(who)) {
					return entry.getKey();
				}
			}
		}

		return null;
	}
	
	public void removePlayerCheckpoint(Player who) {
		Checkpoint chp = getPlayerCheckpoint(who);
		if (chp == null) {
			return;
		}
		playerChecks.get(chp).remove(who);
	}
}
