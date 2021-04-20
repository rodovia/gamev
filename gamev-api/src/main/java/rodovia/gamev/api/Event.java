package rodovia.gamev.api;

import java.util.Optional;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import rodovia.gamev.api.util.Vector3f;

public class Event implements MinigameEvent {

	private boolean started;
	private Plugin plugin;
	private Vector3f startCoordinates, endCoordinates;
	private String name;

	public Event(Plugin plg) {
		plugin = plg;
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
			throw new IllegalStateException("O evento não iniciou ou essa função foi chamada mais de uma vez.");
		this.started = false;
	}

	@Override
	public boolean hasStarted() {
		return started;
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

}
