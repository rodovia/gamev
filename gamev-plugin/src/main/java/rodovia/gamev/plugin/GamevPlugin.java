package rodovia.gamev.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.plugin.java.JavaPlugin;

import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.plugin.commands.CheckpointCommand;
import rodovia.gamev.plugin.commands.GameraEventCommand;

public class GamevPlugin extends JavaPlugin {
	private Map<String, MinigameEvent> events;
	
	@Override
	public void onEnable() {
		events = new HashMap<>();
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getCommand("gameraevento").setExecutor(new GameraEventCommand(this));
		getCommand("gcheckpoint").setExecutor(new CheckpointCommand(this));
	}
	
	@Override
	public void onDisable() {
		events.clear();
	}
	
	public void addEvent(MinigameEvent event) {
		if (event.getName() == null) {
			throw new NullPointerException("event has no name, please define one.");
		}
		
		events.put(event.getName(), event);
	}
	
	public Collection<MinigameEvent> getEvents() {
		return events.values();
	}
	
	public Optional<MinigameEvent> getEvent(String name) {
		return Optional.ofNullable(events.get(name));
	}
	
	public Optional<MinigameEvent> removeEvent(String name) {
		MinigameEvent object = events.remove(name);
		return Optional.ofNullable(object);
	}
}
