package rodovia.gamev.plugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import rodovia.gamev.api.MinigameEvent;

/**
 * A event representing someone who won the event.
 */
public class PlayerWinEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	private Player player;
	private MinigameEvent event;
	private boolean cancel;
	
	public PlayerWinEvent(Player player, MinigameEvent event) {
		this.player = player;
		this.event = event;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public MinigameEvent getEvent() {
		return event;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlersList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;		
	}

}
