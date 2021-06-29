package rodovia.gamev.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import rodovia.gamev.api.setting.OptionManager;
import rodovia.gamev.api.util.Vector3f;
import rodovia.gamev.api.util.checkpoint.CheckpointManager;

public class Event implements MinigameEvent {

	private boolean started;
	private Plugin plugin;
	private Vector3f startCoordinates, endCoordinates;
	private String name;
	private OptionManager optManager;
	private Map<String, Player> players;
	private Set<Player> winners;
	private int duration = 120;
	private CheckpointManager checkpman;

	public Event(Plugin plg) {
		plugin = plg;
		optManager = OptionManager.empty();
		players = new HashMap<>();
		winners = new HashSet<>();
		checkpman = new CheckpointManager();
	}
	
	@Override
	public void withName(String name) {
		this.name = name;		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void start() {
		this.started = true;
	}

	public Server getServer() {
		return plugin.getServer();
	}
	
	@Override
	public void end() throws IllegalStateException {
		if (!started)
			throw new IllegalStateException("O evento n�o iniciou ou essa fun��o foi chamada mais de uma vez.");
		this.started = false;
	}

	@Override
	public boolean hasStarted() {
		return started;
	}

	@Override
	public OptionManager getOptionManager() {
		return optManager;
	}
	
	@Override
	public void setStartCoordinates(Vector3f coords) {
		this.startCoordinates = coords;
	}

	@Override
	public void setEndCoordinates(Vector3f coords) {
		this.endCoordinates = coords;		
	}

	@Override
	public Optional<Vector3f> getStartCoordinates() {
		return Optional.ofNullable(this.startCoordinates);
	}

	@Override
	public Optional<Vector3f> getEndCoordinates() {
		return Optional.ofNullable(this.endCoordinates);
	}

	@Override
	public Collection<Player> getPlayers() {
		return players.values();
	}

	@Override
	public Player addPlayer(Player player) {
		if (!players.containsValue(player)) {
			Player pl = players.put(player.getDisplayName(), player);
			return pl;
		}
		return null;
	}

	@Override
	public Player removePlayer(Player player) {
		return players.remove(player.getName());
	}

	@Override
	public int getDuration() {
		
		return duration;
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
		
	}

	@Override
	public void addWinner(Player player) {
		winners.add(player);
	}

	@Override
	public void removeWinner(Player player) {
		winners.remove(player);
	}
	
	public Set<Player> getWinners() {
		return winners;
	}
	
	@Override
	public CheckpointManager getCheckpointManager() {
		return checkpman;
	}
	
}
