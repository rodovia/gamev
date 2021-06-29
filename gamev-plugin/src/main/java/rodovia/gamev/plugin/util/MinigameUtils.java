package rodovia.gamev.plugin.util;

import java.util.NoSuchElementException;
import java.util.Optional;

import rodovia.gamev.api.MinigameEvent;

public class MinigameUtils {
	private MinigameUtils() {  }
	
	public static MinigameEvent attemptToFetchEvent(Optional<MinigameEvent> ev) {
		MinigameEvent event;
		try {
			event = ev.get();
			return event;
		}
		catch (NoSuchElementException err) {
			return null;
		}
	}
}
