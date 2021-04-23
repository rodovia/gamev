package rodovia.gamev.plugin.util;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import rodovia.gamev.api.MinigameEvent;
import rodovia.gamev.plugin.GamevPlugin;

public class PlayerUtils {
	
	private PlayerUtils() {
		
	}
	
	public static MinigameEvent getAssociatedEvent(Player player, GamevPlugin plugin) {
		Stream<MinigameEvent> filter = plugin.getEvents().stream().filter((event) -> { 
			return event.getPlayers().contains(player);
		});
		MinigameEvent event;
		try {
			event = filter.findFirst().get();
		} catch (NoSuchElementException err) {
			event = null;
		}
		
		return event;
	}
}
